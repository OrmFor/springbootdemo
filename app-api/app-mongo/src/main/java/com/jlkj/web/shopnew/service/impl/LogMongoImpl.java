package com.jlkj.web.shopnew.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantDateType;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.*;
import com.jlkj.web.shopnew.enums.EnumLogType;
import com.jlkj.web.shopnew.enums.EnumUserType;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.pojo.Staff;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.pojo.mongo.CustomerBelongMongo;
import com.jlkj.web.shopnew.pojo.mongo.FollowMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.*;
import com.jlkj.web.shopnew.service.*;
import com.jlkj.web.shopnew.service.mongo.ICustomerBelongMongo;
import com.jlkj.web.shopnew.service.mongo.ILogMongo;
import com.jlkj.web.shopnew.util.DateUtil;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LogMongoImpl implements ILogMongo {
    private static final Logger LOGGER = LogManager.getLogger(LogMongoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IStaff staffService;

    @Autowired
    private IOrders ordersService;

    @Autowired
    private ICompany companyService;

    @Autowired
    private IUser userService;

    @Autowired
    private ICustomerBelongMongo customerBelongMongo;

    @Autowired
    private IManagers managersService;

    @Override
    public Map<String, Object> getMyCustomerLog(GetMyCustomerLogResquest getMyCustomerLogResquest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(getMyCustomerLogResquest.getOperationId()));

        if (StringUtils.isNotBlank(getMyCustomerLogResquest.getDateType())
                && !ConstantDateType.ALL_DAY.equals(getMyCustomerLogResquest.getDateType())) {
            //累计不需要管
            Map<String, Date> startEndDay = DateUtil.getDateByType(
                    getMyCustomerLogResquest.getDateType(),
                    getMyCustomerLogResquest.getStartDay(),
                    getMyCustomerLogResquest.getEndDay()
            );

            criteria = criteria.and("updateTime").gte(
                    DateUtil.getFirstSecIntegralTime(startEndDay.get("startDay"))
            ).lte(
                    DateUtil.getLastSecIntegralTime(startEndDay.get("endDay"))
            );

        }
        if (3 != getMyCustomerLogResquest.getUserType()) {
            criteria = criteria.and("userType").is(getMyCustomerLogResquest.getUserType());
        }

        int page = getMyCustomerLogResquest.getPage() == 0 ? 10 : getMyCustomerLogResquest.getPage();
        int pageSize = getMyCustomerLogResquest.getPageSize() == 0 ? 10 : getMyCustomerLogResquest.getPageSize();

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);

        // 设置查询条件，分页
        Query query = Query.query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "updateTime"))
                .with(pageRequest);
        Long count = mongoTemplate.count(query, LogMongo.class);//查询总记录数
        List<LogMongo> list = this.mongoTemplate.find(query, LogMongo.class);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", count);
        data.put("list", list);
        return data;
    }

    @Override
    public UpdateResult updateFollow(UpdateFollowDto dto) {
        try {
            List<Integer> list = staffService.getStaffUids(dto.getBelongUid());
            Criteria criteria = new Criteria().andOperator(
                    Criteria.where("operationId").in(list),
                    Criteria.where("uid").is(dto.getUid()));
            Query query = Query.query(criteria);

            Update update = new Update();
            update.set("followNum", dto.getCount());
            update.set("lastFollowTime", dto.getLastFollowTime());
            return this.mongoTemplate.updateMulti(query, update, LogMongo.class);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    //需要添加operationRole follow 和 followNum
    @Override
    public void saveLogMongo(SaveLogRequest saveLogRequest) {
        int role = 0 ;
        boolean flag = companyService.companyHasStaff(saveLogRequest.getUid()
                ,saveLogRequest.getOperationId());//是否为员工分享

        //进入分享链接特殊处理(boss的客户==>staff的客户)
        if(1 == saveLogRequest.getIsShare()){
            if(saveLogRequest.getUid() == saveLogRequest.getOperationId()){//如果浏览人为老板自己 则不加入足迹表
                return;
            }

            flag = companyService.companyHasStaff(saveLogRequest.getShareId()
                    ,saveLogRequest.getOperationId());//是否为员工分享

            if(flag){//员工分享 取反，转移客户
                int operationId = saveLogRequest.getOperationId();//被分享的店铺老板
                int shareId = saveLogRequest.getShareId();//分享人-员工
                User user = userService.selectByPrimaryKey(shareId);
                saveLogRequest.setOperationId(shareId);//分享人的链接被点击
                saveLogRequest.setOperationName(user == null ? "" : user.getUsername());//获取员工姓名
                saveLogRequest.setShareId(operationId);
            }
        }


        int companyId = companyService.getCompanyId(saveLogRequest.getOperationId());//获得企业

        if (companyId != -1){
            saveLogRequest.setCompanyId(companyId);//被操作人所在企业
            role = getRole(saveLogRequest,flag);//获得被操作人的角色
        }

        //查询userType
        GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest();
        getUserInfoRequest.setUid(saveLogRequest.getUid());
        getUserInfoRequest.setOperationId(saveLogRequest.getOperationId());
        int userType =  customerBelongMongo.getUserType(getUserInfoRequest);//获取该客户的类型

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(saveLogRequest.getOperationId()),
                Criteria.where("uid").is(saveLogRequest.getUid()),
                Criteria.where("logType").is(saveLogRequest.getLogType()));
        Query query = Query.query(criteria);
        query.with(Sort.by(Sort.Direction.DESC,"id"));//之后时刻点的记录排在前面，取第一个

        LogMongo bean = this.mongoTemplate.findOne(query, LogMongo.class);//获取该客户某行为的最新足迹
        if (bean != null) {
            int type = bean.getLogType();
            if (EnumLogType.LOG_CHAT.getType() == type) {//聊天足迹特殊处理 24小时内都只算一次
                Date date = bean.getUpdateTime();
                Date dateNew = new Date();
                if (DateUtil.daysBetween(dateNew, date) <= 1) {
                    return;
                }
            }
            long num = bean.getNum();
            saveLogRequest.setNum(num + 1L);
        } else {
            saveLogRequest.setNum(1L);
        }


        if(EnumLogType.LOG_BUY.getType() == saveLogRequest.getLogType()){//商城下单 标识为真实客户
            userType = 2;
        }

        //保存log足迹
        User user = userService.selectByPrimaryKey(saveLogRequest.getUid());
        saveLogRequest.setCreateTime(DateUtil.getMongoDate());//new Date
        saveLogRequest.setUpdateTime(DateUtil.getMongoDate());//new Date
        saveLogRequest.setUserType(userType);//客户类型
        saveLogRequest.setUserImg(user == null ? "" : user.getLogo() == null ? "" : user.getLogo());//客户头像
        saveLogRequest.setOperationRole(role);//被操作人角色
        saveLogRequest.setTransfer(0);

        UpdateFollowDto dto = addFollowNum(saveLogRequest);//followMongo数据==>logMongo
        saveLogRequest.setFollowNum((int) dto.getCount());
        saveLogRequest.setLastFollowTime(dto.getLastFollowTime());
        this.mongoTemplate.save(saveLogRequest, Constant.LOGMONGO);

        //保存customerBelong
        if(user != null) {
            CustomerBelongMongo belongMongo = new CustomerBelongMongo();
            belongMongo.setPhone(user.getPhone());
            belongMongo.setUserName(user.getUsername());
            belongMongo.setUid(user.getId());
            belongMongo.setBelongUserName(saveLogRequest.getOperationName());
            belongMongo.setBelongUid(saveLogRequest.getOperationId());
            belongMongo.setUserType(userType);
            belongMongo.setTransfer(0);//标识转移
            customerBelongMongo.saveCustomerBelong(belongMongo);
        }
    }



    private int getRole(SaveLogRequest saveLogRequest,boolean flag){

        if(flag &&  1!= saveLogRequest.getIsShare()){
            return staffService.getRole(saveLogRequest.getUid());
        }else if(flag &&  1 == saveLogRequest.getIsShare()){
            return staffService.getRole(saveLogRequest.getOperationId());
        }

        return 0;
    }

    //followMongo数据==>logMongo
    private UpdateFollowDto addFollowNum(SaveLogRequest saveLogRequest){
        List<Integer> list = staffService.getStaffUids(saveLogRequest.getOperationId());//获得员工列表
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(saveLogRequest.getUid()),
                Criteria.where("belongUid").in(list));

        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query, FollowMongo.class);//某客户的跟进记录数(于公司而言)
        Date date = new Date();
        if(count == 0){
            return UpdateFollowDto.builder()
                    .belongUid(saveLogRequest.getOperationId())
                    .uid(saveLogRequest.getUid()).lastFollowTime(date)
                    .count(0).build();


        }

        //某客户的最新跟进记录
        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                Aggregation.group().max("updateTime").as("updateTime"),
                Aggregation.project("updateTime").andExclude("_id")
        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();

        if(mappedResults != null) {
            date = mappedResults.get(0).getDate("updateTime");//某客户的最新跟进时间
        }
        return UpdateFollowDto.builder()
                .belongUid(saveLogRequest.getOperationId())
                .uid(saveLogRequest.getUid()).lastFollowTime(date == null ? new Date() : date)
                .count(count).build();

    }

    @Override
    public Map<String, Object> getListByLogType(GetListByLogTypeRequest getListByLogTypeRequest) {
        String type = getListByLogTypeRequest.getDateType();
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").is(getListByLogTypeRequest.getLogType())
        );
        Map<String, Date> date =
                DateUtil.getDateByType(type, getListByLogTypeRequest.getStartDay(), getListByLogTypeRequest.getEndDay());

        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));
        int page = getListByLogTypeRequest.getPage() == 0 ? 1 : getListByLogTypeRequest.getPage();
        int pageSize = getListByLogTypeRequest.getPageSize() == 0 ? 10 : getListByLogTypeRequest.getPageSize();
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "updateTime" ));

        // 设置查询条件，分页
        Query query = Query.query(criteria).with(pageRequest);
        Long count = mongoTemplate.count(query, LogMongo.class);//查询总记录数
        List<LogMongo> list = this.mongoTemplate.find(query, LogMongo.class);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", count);
        data.put("list", list);
        return data;
    }

    //获取指定logType下的所有用户对自己商城的足迹
    @Override
    public Map<String, Object> getLogListByLogTypeAndUid(GetLogListByLogTypeAndUidRequest getLogListByLogTypeAndUidRequest) {
        String type = getLogListByLogTypeAndUidRequest.getDateType();

        Map<String,Object> map=new HashMap<>();
        int totalCount=0;
        int page = getLogListByLogTypeAndUidRequest.getPage() == 0 ? 1 : getLogListByLogTypeAndUidRequest.getPage();
        int pageSize = getLogListByLogTypeAndUidRequest.getPageSize() == 0 ? 10 : getLogListByLogTypeAndUidRequest.getPageSize();

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").is(getLogListByLogTypeAndUidRequest.getLogType())
        );

        if (getLogListByLogTypeAndUidRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(getLogListByLogTypeAndUidRequest.getUid());
            criteria.and("operationId").in(list);//boss 所有员工
        } else {
            criteria.and("operationId").is(getLogListByLogTypeAndUidRequest.getUid());//staff 自己
        }

        DateDto dateDto =
                DateUtil.getDayByType(type, getLogListByLogTypeAndUidRequest.getStartDay(),
                        getLogListByLogTypeAndUidRequest.getEndDay());

        criteria.and("updateTime").gte(dateDto.getStartDay()).lte(dateDto.getEndDay());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","operationId").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","operationId").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("operationName").as("operationName")
                        .first("num").as("num")
                        .first("userImg").as("userImg")
                        .first("followNum").as("followNum")
                        .first("lastFollowTime").as("lastFollowTime")
                        .first("goodName").as("goodName")
                        .first("userType").as("userType")
                        .first("logType").as("logType"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        return map;
    }

    @Override
    public Map<String, Object> getLogCountByLogTypeAndUid(GetLogCountByLogTypeAndUidRequest getLogCountByLogTypeAndUidRequest) {
        String type = getLogCountByLogTypeAndUidRequest.getDateType();
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").in(getLogCountByLogTypeAndUidRequest.getLogTypes())
        );

        if (getLogCountByLogTypeAndUidRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(getLogCountByLogTypeAndUidRequest.getUid());
            criteria.and("operationId").in(list);
        } else {
            criteria.and("operationId").is(getLogCountByLogTypeAndUidRequest.getUid());
        }

        DateDto dateDto =
                DateUtil.getDayByType(type, getLogCountByLogTypeAndUidRequest.getStartDay(), getLogCountByLogTypeAndUidRequest.getEndDay());

        criteria.and("updateTime").gte(dateDto.getStartDay()).lte(dateDto.getEndDay());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("logType").count().as("count")
                        .first("logType").as("logType"),
                Aggregation.project("count", "logType").andExclude("_id")
        );


        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();

        LOGGER.info(mappedResults);

        return null;


    }

    @Override
    public Map<String, Object> getLogListByUid(GetListByUidRequest getListByUidRequest) {

        List<Integer> listStaff = staffService.getStaffUids(getListByUidRequest.getUid());
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").in(listStaff)
        );
        int page = getListByUidRequest.getPage() == 0 ? 1 : getListByUidRequest.getPage();
        int pageSize = getListByUidRequest.getPageSize() == 0 ? 10 : getListByUidRequest.getPageSize();
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "updateTime"));

        Query query = Query.query(criteria).with(pageRequest);
        Long count = mongoTemplate.count(query, LogMongo.class);//查询总记录数
        List<LogMongo> list = this.mongoTemplate.find(query, LogMongo.class);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", count);
        data.put("list", list);
        return data;
    }

    //我的跟进等统计信息
    @Override
    public Map<String, Object> getMyFollowStatics(GetMyFollowStatiscRequest getMyFollowStatiscRequest) {

        List<JSONObject> userTypeStaticsResult = new ArrayList<>();
        JSONObject getUserType0 = getUserType(0, getMyFollowStatiscRequest.getUid());
        JSONObject getUserType1 = getUserType(1, getMyFollowStatiscRequest.getUid());
        JSONObject getUserType2 = getUserType(2, getMyFollowStatiscRequest.getUid());
        userTypeStaticsResult.add(getUserType0);//"我"的潜在客户统计信息
        userTypeStaticsResult.add(getUserType1);//"我"的意向客户统计信息
        userTypeStaticsResult.add(getUserType2);//"我"的真实客户统计信息


        List<JSONObject> getFollowByUserTypeResult = getFollowByUserType(getMyFollowStatiscRequest);//不同类型客户跟进统计信息
        List<JSONObject> getChatStaticsResult = getChatStatics(getMyFollowStatiscRequest);//和"我"相关的聊天统计信息
        List<JSONObject> getMyFollowCountResult = getMyFollowCount(getMyFollowStatiscRequest);//和"我"相关的跟进统计信息

        Map<String, Object> data = new HashMap<>();
        data.put("userTypeStatics", userTypeStaticsResult);//潜在客户  意向客户  客户
        data.put("getFollowByUserTypeResult", getFollowByUserTypeResult);
        data.put("getChatStaticsResult", getChatStaticsResult);
        data.put("getMyFollowCountResult", getMyFollowCountResult);
        return data;
    }


    //统计潜在客户  意向客户  客户
    private JSONObject getUserType(int type, int uid) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(uid),
                Criteria.where("updateTime").lte(new Date()),
                Criteria.where("updateTime").gte(DateUtil.rollDay(new Date(), -10))
        );
        criteria.and("userType").is(type);
        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num")
                        .first("userType").as("userType"),
                Aggregation.project("num", "userType").andExclude("_id")
        );
        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, Constant.CUSTOMERBELONGMONGO, JSONObject.class);
        List<JSONObject> userTypeStaticsResult = results.getMappedResults();
        return (userTypeStaticsResult == null || userTypeStaticsResult.size() == 0) ?
                new JSONObject() :
                userTypeStaticsResult.get(0);
    }


    //统计跟进人数  意向客户跟进  潜在客户跟进
    private List<JSONObject> getFollowByUserType(GetMyFollowStatiscRequest getMyFollowStatiscRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(getMyFollowStatiscRequest.getUid()),
                Criteria.where("updateTime").lte(new Date()),
                Criteria.where("updateTime").gte(DateUtil.rollDay(new Date(), -10))
        );

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("userType", "belongUid").count().as("num") //belongUid分组字段唯一，无影响
                        .first("userType").as("userType"),
                Aggregation.project("num", "userType").andExclude("_id")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        return mappedResults;
    }

    //统计聊天人数
    public List<JSONObject> getChatStatics(GetMyFollowStatiscRequest getMyFollowStatiscRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(getMyFollowStatiscRequest.getUid()),
                Criteria.where("logType").is(EnumLogType.LOG_CHAT.getType()),
                Criteria.where("updateTime").lte(new Date()),
                Criteria.where("updateTime").gte(DateUtil.rollDay(new Date(), -10))
        );
        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num")
                        .first("userType").as("userType"),
                Aggregation.project("num", "userType").andExclude("_id")

        );
        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        return mappedResults;
    }


    //统计我的跟进数
    public List<JSONObject> getMyFollowCount(GetMyFollowStatiscRequest getMyFollowStatiscRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(getMyFollowStatiscRequest.getUid()),
                Criteria.where("updateTime").lte(new Date()),
                Criteria.where("updateTime").gte(DateUtil.rollDay(new Date(), -10))
        );
        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num")
                        .first("userType").as("userType"),
                Aggregation.project("num", "userType").andExclude("_id")

        );
        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        return mappedResults;
    }


    //统计所有
    @Override
    public Map<String, Object> getStatics(StatiscRequest statiscRequest) {
        DateDto dateDto = DateUtil.getDayByType(statiscRequest.getDateType(),
                statiscRequest.getStartDay(), statiscRequest.getEndDay());//处理后的日期时间


        Map<String, Object> data = new HashMap<>();
        StaticsDto countDto = new StaticsDto();
        //统计潜在客户人数
        //统计新增潜在客户数
        int potentialUserCountResultNew = getUserTypeCountNew(statiscRequest,dateDto,0);
        int potentialUserAddCountResultNew = getUserTypeAddCountNew(statiscRequest, dateDto, 0);

        //int potentialUserCountResult = getUserTypeCount(statiscRequest, dateDto, "potentialTime");
//        int potentialUserAddCountResult = getUserTypeAddCount(statiscRequest, dateDto, "potentialTime");

        //统计意向客户人数
        //统计新增意向客户人数
        int intentionalUserCountResultNew =  getUserTypeCountNew(statiscRequest,dateDto,1);
        int intentionalUserAddCountResultNew = getUserTypeAddCountNew(statiscRequest, dateDto, 1);

        // int intentionalUserCountResult = getUserTypeCount(statiscRequest, dateDto, "intentionalTime");
        //统计新增意向客户数
        //  int intentionalUserAddCountResult = getUserTypeAddCount(statiscRequest, dateDto, "intentionalTime");


        //统计客户人数
        //统计新增
        int customerUserCountResultNew =  getUserTypeCountNew(statiscRequest,dateDto,2);
        int customerUserAddCountResultNew = getUserTypeAddCountNew(statiscRequest, dateDto, 2);

        //int customerUserCountResult = getUserTypeCount(statiscRequest, dateDto, "customerTime");
        //统计新增客户数
        // int customerUserAddCountResult = getUserTypeAddCount(statiscRequest, dateDto, "customerTime");

        //统计聊天人数
        //统计新增聊天人数
        int chatCountResult = getChatCount(statiscRequest, dateDto);
        int chatAddCountResult = getChatAddCount(statiscRequest, dateDto);


        //统计潜在客户跟进  意向客户跟进
        List<StaticsCountDto> getUserTypeFollowCountResult = getUserTypeFollowCount(statiscRequest, dateDto);
        int potentialUserFollowCountResult = 0;
        int intentionalUserFollowCountResult = 0;
        if (getUserTypeFollowCountResult == null || getUserTypeFollowCountResult.size() == 0) {
        } else {

            for (StaticsCountDto dto : getUserTypeFollowCountResult) {
                //0表示潜在客户
                if (dto.getStaticsType() == 0) {
                    potentialUserFollowCountResult = dto.getCount();//和"我"相关的潜在客户统计数
                }

                //1表示意向客户
                if (dto.getStaticsType() == 1) {
                    intentionalUserFollowCountResult = dto.getCount();//和"我"相关的意向客户统计数
                }
            }
        }

        List<StaticsCountDto> getUserTypeFollowAddCountResult = getUserTypeFollowAddCount(statiscRequest, dateDto);

        int potentialUserFollowAddCountResult = 0;
        int intentionalUserFollowAddCountResult = 0;
        if (getUserTypeFollowAddCountResult == null || getUserTypeFollowAddCountResult.size() == 0) {
        } else {

            for (StaticsCountDto dto : getUserTypeFollowCountResult) {
                //0表示潜在客户
                if (dto.getStaticsType() == 0) {
                    potentialUserFollowAddCountResult = dto.getCount();
                }

                //1表示意向客户
                if (dto.getStaticsType() == 1) {
                    intentionalUserFollowAddCountResult = dto.getCount();
                }
            }
        }


        //统计咨询跟进数
        //统计新增咨询跟进数
        Integer followAfterChatCountResult = getFollowAfterChatCount(statiscRequest, dateDto);
        Integer followAfterChatAddCountResult = getFollowAfterChatAddCount(statiscRequest, dateDto);

        //统计订单数
        //统计新增订单数
        Integer orderCount = ordersService.getOrdersCount(statiscRequest,dateDto);
        Integer orderAddCount = ordersService.getOrdersAddCount(statiscRequest,dateDto);

        //统计商业总额
        //统计新增商业总额
        BigDecimal realPriceCount = ordersService.getRealPriceCount(statiscRequest,dateDto);
        BigDecimal realPriceAddCount = ordersService.getRealPriceAddCount(statiscRequest,dateDto);


        countDto = StaticsDto.builder()
                //统计用户相关
                .potentialUserCountResult(potentialUserCountResultNew)
                .potentialUserAddCountResult(potentialUserAddCountResultNew)

                .intentionalUserCountResult(intentionalUserCountResultNew)
                .intentionalUserAddCountResult(intentionalUserAddCountResultNew)

                .customerUserCountResult(customerUserCountResultNew)
                .customerUserAddCountResult(customerUserAddCountResultNew)
                //统计咨询数
                .chatAddCountResult(chatAddCountResult)
                .chatCountResult(chatCountResult)

                //统计跟进数
                .potentialUserFollowCountResult(potentialUserFollowCountResult)
                .potentialUserFollowAddCountResult(potentialUserFollowAddCountResult)

                .intentionalUserFollowCountResult(intentionalUserFollowCountResult)
                .intentionalUserFollowAddCountResult(intentionalUserFollowAddCountResult)

                //统计咨询跟进数
                .followAfterChatCountResult(followAfterChatCountResult)
                .followAfterChatAddCountResult(followAfterChatAddCountResult)

                //统计订单数
                .orderAddCountResult(orderAddCount)
                .orderCountResult(orderCount)

                //统计商业额
                .amountCountResult(realPriceCount)
                .amountAddCountResult(realPriceAddCount)
                .build();

        data.put("count", countDto);
        return data;
    }

    /////////////////////////////////////统计chat/////////////////////////////////////////////////
    @Override
    public Integer getChatCount(StatiscRequest statiscRequest,DateDto dateDto) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").is(EnumLogType.LOG_CHAT.getType()));
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {//累计
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }


        if (statiscRequest.isBoss()) {//统计公司的
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("operationId").in(list);
        } else {//统计普通员工个人的
            criteria.and("operationId").is(statiscRequest.getUid());
        }

        long count = mongoTemplate.count(Query.query(criteria),LogMongo.class);

        return (int) count;

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")

        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();

        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/

    }

    //统计聊天新增
    @Override
    public Integer getChatAddCount(StatiscRequest statiscRequest,DateDto dateDto) {

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").is(EnumLogType.LOG_CHAT.getType()),
                Criteria.where("updateTime").lte(dateDto.getEndAddDay()),
                Criteria.where("updateTime").gte(dateDto.getStartAddDay())
        );

        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("operationId").in(list);
        } else {
            criteria.and("operationId").is(statiscRequest.getUid());
        }

        long count = mongoTemplate.count(Query.query(criteria),LogMongo.class);

        return (int) count;

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")

        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();

        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }

    ///////////////////////////////////////////chatEnd////////////////////////////////////////////////////
    public Integer getUserTypeAddCountNew(StatiscRequest statiscRequest, DateDto dateDto, int userType) {

        Criteria criteria = new Criteria();
       /* if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {*/
            criteria.and("updateTime").lte(dateDto.getEndAddDay()).gte(dateDto.getStartAddDay());
       // }

        criteria.and("userType").is(userType);
        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("operationId").in(list);
        } else {
            criteria.and("operationId").is(statiscRequest.getUid());
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group("uid").count().as("num")
                        .first("userType").as("userType"),
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );


        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg,Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();

        if(mappedResults != null && mappedResults.size() > 0){
            LogMongo logMongo = mappedResults.get(0);
            return (int) logMongo.getNum();
        }

        return 0;

        /*
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.CUSTOMERBELONGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }

    //按客户类型统计
    @Override
    public Integer getUserTypeCountNew(StatiscRequest statiscRequest, DateDto dateDto, int userType) {
        Criteria criteria = new Criteria();
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {//累计
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        criteria.and("userType").is(userType);//0潜在客户 1意向客户 2真实客户
        if (statiscRequest.isBoss()) {//统计企业的
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("operationId").in(list);
        } else {//统计普通员工个人的
            criteria.and("operationId").is(statiscRequest.getUid());
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group("uid").count().as("num")
                        .first("userType").as("userType"),
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg,Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();

        if(mappedResults != null && mappedResults.size() > 0){
            LogMongo logMongo = mappedResults.get(0);
            return (int) logMongo.getNum();
        }

        return 0;

    }


    //统计潜在客户
    @Override
    public Integer getUserTypeCount(StatiscRequest statiscRequest, DateDto dateDto, String timeType) {
        Criteria criteria = new Criteria();
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {//累计 取一端
            criteria.and(timeType).lte(dateDto.getEndDay());
        } else {//非累计 昨天..
            criteria.and(timeType).lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        if (statiscRequest.isBoss()) {//统计公司的
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {//统计普通员工个人
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

        long count = mongoTemplate.count(Query.query(criteria),CustomerBelongMongo.class);//客户记录

        return (int) count;

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg,
                Constant.CUSTOMERBELONGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/

    }

    //统计各个类型的用户增量
    @Override
    public Integer getUserTypeAddCount(StatiscRequest statiscRequest, DateDto dateDto, String timeType) {

        Criteria criteria = new Criteria();

        criteria.andOperator(
                Criteria.where(timeType).lte(dateDto.getEndAddDay()),
                Criteria.where(timeType).gte(dateDto.getStartAddDay())
        );


        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );*/

        long count = mongoTemplate.count(Query.query(criteria),CustomerBelongMongo.class);

        return (int) count;

        /*
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.CUSTOMERBELONGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }


    /////////////////////////////////////////客户统计///////////////////////////////////////////

    ///////////////////////////////////统计客户跟进//////////////////////////////////////////
    @Override
    public List<StaticsCountDto> getUserTypeFollowCount(StatiscRequest statiscRequest, DateDto dateDto) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("userType").in(Lists.newArrayList(0, 1))
        );
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }


        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group("userType","uid").count().as("count").first("userType").as("staticsType"),
                Aggregation.project("count", "staticsType").andExclude("_id")
        );
        AggregationResults<StaticsCountDto> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, StaticsCountDto.class);
        List<StaticsCountDto> mappedResults = results.getMappedResults();
        return mappedResults;
    }



    @Override
    public List<StaticsCountDto> getUserTypeFollowAddCount(StatiscRequest statiscRequest, DateDto dateDto) {

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("userType").in(Lists.newArrayList(0, 1)),
                Criteria.where("updateTime").lte(dateDto.getEndDay()),
                Criteria.where("updateTime").gte(dateDto.getStartDay())
        );

        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group("userType","uid").count().as("count")
                        .first("userType").as("staticsType"),
                Aggregation.project("count", "staticsType").andExclude("_id")
        );
        AggregationResults<StaticsCountDto> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, StaticsCountDto.class);
        List<StaticsCountDto> mappedResults = results.getMappedResults();
        return mappedResults;
    }

    ///////////////////////////////////统计客户跟进//////////////////////////////////////////



    //////////////////////////////////////////咨询跟进////////////////////////////////////////////////////
    //统计跟进表
    @Override
    public Integer getFollowAfterChatCount(StatiscRequest statiscRequest, DateDto dateDto) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("isAfterChat").is(1)
        );
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());

        }

        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

        long count = mongoTemplate.count(Query.query(criteria),FollowMongo.class);

        return (int) count;

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }

    //统计跟进新增
    @Override
    public Integer getFollowAfterChatAddCount(StatiscRequest statiscRequest, DateDto dateDto) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("isAfterChat").is(1),
                Criteria.where("updateTime").lte(dateDto.getEndDay()),
                Criteria.where("updateTime").gte(dateDto.getStartDay())
        );

        if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {
            criteria.and("belongUid").is(statiscRequest.getUid());
        }

        long count = mongoTemplate.count(Query.query(criteria),FollowMongo.class);

        return (int) count;

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.FOLLOWMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }


    //////////////////////////////////////////咨询跟进////////////////////////////////////////////////////


    //统计不同行为的记录数(时间+行为类型分组统计)
    @Override
    public Map<String, Object> getLogListCountByLogType(GetLogCountByLogTypeAndUidRequest getLogCountByLogTypeAndUidRequest) {
        DateDto dateDto = DateUtil.getDayByType(getLogCountByLogTypeAndUidRequest.getDateType(),
                getLogCountByLogTypeAndUidRequest.getStartDay(), getLogCountByLogTypeAndUidRequest.getEndDay());

        List<Integer> logTypeList=Lists.newArrayList();
        int[] logTypes = getLogCountByLogTypeAndUidRequest.getLogTypes();
        if(logTypes.length!=0){
            for(int log:logTypes){
                logTypeList.add(log);
            }
        }

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").in(logTypeList)
        );

        if (getLogCountByLogTypeAndUidRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(getLogCountByLogTypeAndUidRequest.getUid());
            criteria.and("operationId").in(list);
        } else {//只统计员工自己
            criteria.and("operationId").is(getLogCountByLogTypeAndUidRequest.getUid());
        }
        criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());

        int companyId = companyService.getCompanyId(getLogCountByLogTypeAndUidRequest.getUid());
        if(companyId!=-1){
            criteria.and("companyId").is(companyId);
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group("logType").count().as("num")
                        .first("logType").as("logType"),
                Aggregation.project("num","logType").andExclude("_id")
        );


        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("list",mappedResults);
        return map;
    }

    @Override
    public Map<String, Object> getLogListByCustomer(GetLogListByCustomerRequest getLogListByCustomerRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").in(getLogListByCustomerRequest.getUid())
        );
        int page = getLogListByCustomerRequest.getPage() == 0 ? 1 : getLogListByCustomerRequest.getPage();
        int pageSize = getLogListByCustomerRequest.getPageSize() == 0 ? 10 : getLogListByCustomerRequest.getPageSize();
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "updateTime"));
        // 设置查询条件，分页
        Query query = Query.query(criteria).with(pageRequest);
        Long count = mongoTemplate.count(query, LogMongo.class);//查询总记录数
        List<LogMongo> list = this.mongoTemplate.find(query, LogMongo.class);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", count);
        data.put("list", list);
        return data;
    }

    public Map<String, Object> getLogListByCustomerNew(GetLogListByCustomerRequest getLogListByCustomerRequest) {
        int page = getLogListByCustomerRequest.getPage() == 0 ? 1 : getLogListByCustomerRequest.getPage();
        int pageSize = getLogListByCustomerRequest.getPageSize() == 0 ? 10 : getLogListByCustomerRequest.getPageSize();
        int totalCount=0;

        Map<String, Object> map=new HashMap<String,Object>();

        Criteria criteria = new Criteria();
        if(getLogListByCustomerRequest.isBoss()){
            List<Integer> list = staffService.getCompanyStaff(getLogListByCustomerRequest.getOperationId());

            criteria.and("operationId").in(list);
        }else{
            criteria.and("operationId").is(getLogListByCustomerRequest.getOperationId());//and 会自动去除第一个多余的
        }
        criteria.and("uid").is(getLogListByCustomerRequest.getUid());

        int companyId = companyService.getCompanyId(getLogListByCustomerRequest.getOperationId());
        if(companyId!=-1){
            criteria.and("companyId").is(companyId);
        }

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType","goodName").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType","goodName").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("operationName").as("operationName")
                        .first("num").as("num")
                        .first("userImg").as("userImg")
                        .first("followNum").as("followNum")
                        .first("lastFollowTime").as("lastFollowTime")
                        .first("goodName").as("goodName"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);
//        for (LogMongo logMongo:mappedResults){
//            LOGGER.info("[bug]"+logMongo);
//        }
        return map;
    }

    //获取我的客户足迹 新
    public Map<String, Object> getMyCustomerLogNew(GetMyCustomerLogResquest getMyCustomerLogResquest) {

        int page = getMyCustomerLogResquest.getPage() == 0 ? 1 : getMyCustomerLogResquest.getPage();
        int pageSize = getMyCustomerLogResquest.getPageSize() == 0 ? 10 : getMyCustomerLogResquest.getPageSize();
        int totalCount=0;

        Map<String, Object> map = new HashMap<>();

        //获取相关企业下的所有员工
        List<Integer> listStaff = staffService.getStaffUids(getMyCustomerLogResquest.getOperationId());
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").in(listStaff)
        );

        int companyId = companyService.getCompanyId(getMyCustomerLogResquest.getOperationId());
        if(companyId!=-1){
            criteria.and("companyId").is(companyId);
        }

        /*Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(getMyCustomerLogResquest.getOperationId()));*/

        if (StringUtils.isNotBlank(getMyCustomerLogResquest.getDateType())
                && !ConstantDateType.ALL_DAY.equals(getMyCustomerLogResquest.getDateType())) {
            //累计不需要管
            DateDto  dateDto = DateUtil.getDayByType(
                    getMyCustomerLogResquest.getDateType(),
                    getMyCustomerLogResquest.getStartDay(),
                    getMyCustomerLogResquest.getEndDay()
            );
            //只考虑非累计 两端
            criteria = criteria.and("updateTime").gte(dateDto.getStartDay()).lte(dateDto.getEndDay());
        }

        if (3 != getMyCustomerLogResquest.getUserType()) {
            criteria = criteria.and("userType").is(getMyCustomerLogResquest.getUserType());//指定客户类型
        }

        //某客户某种行为的最新记录
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType","userType").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType","userType").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("operationName").as("operationName")
                        .first("num").as("num")
                        .first("userImg").as("userImg")
                        .first("followNum").as("followNum")
                        .first("lastFollowTime").as("lastFollowTime")
                        .first("goodName").as("goodName")
                        .first("userType").as("userType")
                        .first("logType").as("logType"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        return map;
    }

    //获取指定logType下的所有用户 新
    public Map<String, Object> getListByLogTypeNew(GetListByLogTypeRequest getListByLogTypeRequest) {
        String type = getListByLogTypeRequest.getDateType();
        int page = getListByLogTypeRequest.getPage() == 0 ? 1 : getListByLogTypeRequest.getPage();
        int pageSize = getListByLogTypeRequest.getPageSize() == 0 ? 10 : getListByLogTypeRequest.getPageSize();
        int totalCount=0;
        Map<String,Object> map=new HashMap<>();

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("logType").is(getListByLogTypeRequest.getLogType())
        );
        Map<String, Date> date =
                DateUtil.getDateByType(type, getListByLogTypeRequest.getStartDay(), getListByLogTypeRequest.getEndDay());

        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("operationName").as("operationName")
                        .first("num").as("num")
                        .first("userImg").as("userImg")
                        .first("followNum").as("followNum")
                        .first("lastFollowTime").as("lastFollowTime")
                        .first("goodName").as("goodName")
                        .first("logType").as("logType"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        return map;
    }

    //获取指定用户下的所有足迹 新
    public Map<String, Object> getLogListByUidNew(GetListByUidRequest getListByUidRequest) {
        int page = getListByUidRequest.getPage() == 0 ? 1 : getListByUidRequest.getPage();
        int pageSize = getListByUidRequest.getPageSize() == 0 ? 10 : getListByUidRequest.getPageSize();
        int totalCount=0;
        Map<String,Object> map=new HashMap<>();

        List<Integer> listStaff = staffService.getStaffUids(getListByUidRequest.getUid());
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").in(listStaff)
        );

        int companyId = companyService.getCompanyId(listStaff.get(0));
        if(companyId!=-1){
            criteria.and("companyId").is(companyId);
        }

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("operationName").as("operationName")
                        .first("num").as("num")
                        .first("userImg").as("userImg")
                        .first("followNum").as("followNum")
                        .first("lastFollowTime").as("lastFollowTime")
                        .first("goodName").as("goodName")
                        .first("logType").as("logType"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        return map;
    }

    /**
     * 获取指定员工相关的所有跟进记录
     * @param getLogListByLogTypeAndStaffId
     * @return
     */
    public Map<String, Object> getFollowListByLogType(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        int page = getLogListByLogTypeAndStaffId.getPage() == 0 ? 1 : getLogListByLogTypeAndStaffId.getPage();
        int pageSize = getLogListByLogTypeAndStaffId.getPageSize() == 0 ? 10 : getLogListByLogTypeAndStaffId.getPageSize();
        Map<String,Object> map=new HashMap<>();

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(getLogListByLogTypeAndStaffId.getStaffId())
        );

        Map<String, Date> date =
                DateUtil.getDateByType(getLogListByLogTypeAndStaffId.getDateType(), getLogListByLogTypeAndStaffId.getStartDay(), getLogListByLogTypeAndStaffId.getEndDay());
        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "updateTime"));

        Query query = Query.query(criteria).with(pageRequest);
        Long totalCount = mongoTemplate.count(query, FollowMongo.class);//查询分页总记录数
        List<FollowMongo> list = this.mongoTemplate.find(query, FollowMongo.class);
        List<FollowMongoDto> dtoList=new ArrayList<>();
        for(FollowMongo followMongo:list){
            User userCondi=new User();
            userCondi.setId(followMongo.getUid());
            User user=userService.getByCondition(userCondi);

            FollowMongoDto followMongoDto=new FollowMongoDto();
            followMongoDto.setContext(followMongo.getContext());
            followMongoDto.setCreateTime(followMongo.getCreateTime());
            followMongoDto.setUserName(followMongo.getUserName());
            followMongoDto.setLogo(user.getLogo());

            dtoList.add(followMongoDto);
        }

        map.put("totalCount",totalCount);
        map.put("list",dtoList);

        LOGGER.info("[===获取指定员工相关的所有跟进记录 totalCount===]"+totalCount);
        LOGGER.info("[===获取指定员工相关的所有跟进记录 dtoList===]"+dtoList);

        return map;
    }

    /**
     * 获取指定员工相关的分享记录
     * @param getLogListByLogTypeAndStaffId
     * @return
     */
    public Map<String, Object> getShareListByLogTypeAndStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        int page = getLogListByLogTypeAndStaffId.getPage() == 0 ? 1 : getLogListByLogTypeAndStaffId.getPage();
        int pageSize = getLogListByLogTypeAndStaffId.getPageSize() == 0 ? 10 : getLogListByLogTypeAndStaffId.getPageSize();
        Map<String,Object> map=new HashMap<>();

        int companyId = companyService.getCompanyId(getLogListByLogTypeAndStaffId.getStaffId());

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(getLogListByLogTypeAndStaffId.getStaffId())
        );

        Map<String, Date> date =
                DateUtil.getDateByType(getLogListByLogTypeAndStaffId.getDateType(), getLogListByLogTypeAndStaffId.getStartDay(), getLogListByLogTypeAndStaffId.getEndDay());
        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));
        criteria.and("logType").in(Lists.newArrayList(EnumLogType.LOG_SHOP.getType(),EnumLogType.LOG_SHOP_SHARE.getType()));

        if(companyId != -1){
            criteria.and("companyId").is(companyId);
        }
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "updateTime"));

        Query query = Query.query(criteria).with(pageRequest);
        Long totalCount = mongoTemplate.count(query, LogMongo.class);//查询分页总记录数
        List<LogMongo> list = this.mongoTemplate.find(query, LogMongo.class);

        map.put("totalCount",totalCount);
        map.put("list",list);

        LOGGER.info("[===获取指定员工相关的分享记录 totalCount===]"+totalCount);
        LOGGER.info("[===获取指定员工相关的分享记录 list===]"+list);

        return map;
    }

    /**
     * 获取指定员工相关的聊天记录
     * @param getLogListByLogTypeAndStaffId
     * @return
     */
    public Map<String,Object> getChartListByLogTypeAndStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        int page = getLogListByLogTypeAndStaffId.getPage() == 0 ? 1 : getLogListByLogTypeAndStaffId.getPage();
        int pageSize = getLogListByLogTypeAndStaffId.getPageSize() == 0 ? 10 : getLogListByLogTypeAndStaffId.getPageSize();
        int totalCount=0;
        Map<String,Object> map=new HashMap<>();

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(getLogListByLogTypeAndStaffId.getStaffId())
        );

        int companyId = companyService.getCompanyId(getLogListByLogTypeAndStaffId.getStaffId());
        if(companyId!=-1){
            criteria.and("companyId").is(companyId);
        }

        Map<String, Date> date =
                DateUtil.getDateByType(getLogListByLogTypeAndStaffId.getDateType(), getLogListByLogTypeAndStaffId.getStartDay(), getLogListByLogTypeAndStaffId.getEndDay());
        criteria.and("updateTime").gte(date.get("startDay")).lte(date.get("endDay"));
        criteria.and("logType").is(EnumLogType.LOG_CHAT.getType());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria)
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list",Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
                        .first("userName").as("userName")
                        .first("userImg").as("userImg"),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.skip((page-1)*pageSize),
                Aggregation.limit(pageSize)
        );

        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        LOGGER.info("[===获取指定员工相关的聊天记录 totalCount===]"+totalCount);
        LOGGER.info("[===获取指定员工相关的聊天记录 mappedResults===]"+mappedResults);

        return map;
    }

    //统计各员工咨询数
    public int getChatCount(int staffId,DateDto dateDto){
        int totalCount=0;

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(staffId)
        );

        criteria.and("updateTime").gte(dateDto.getStartDay()).lte(dateDto.getEndDay());
        criteria.and("logType").is(EnumLogType.LOG_CHAT.getType());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria)
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            return 0;
        }

        totalCount=results.getMappedResults().size();
        return totalCount;
    }

    //统计各员工分享数
    public int getShareCount(int staffId,DateDto dateDto){
        int totalCount=0;

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(staffId)
        );

        criteria.and("updateTime").gte(dateDto.getStartDay()).lte(dateDto.getEndDay());
        criteria.and("logType").in(EnumLogType.LOG_SHOP.getType(),EnumLogType.LOG_SHOP_SHARE.getType());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria)
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            return 0;
        }

        totalCount=results.getMappedResults().size();
        return totalCount;
    }

    //boss总览:统计所有 新
    public ResultCode getStaticsNew(StatiscRequest statiscRequest) {
        DateDto dateDto = DateUtil.getDayByType(statiscRequest.getDateType(),
                statiscRequest.getStartDay(), statiscRequest.getEndDay());//处理后的日期时间

        Map<String, Object> data = new HashMap<>();
        StaticsDto countDto = new StaticsDto();

        int mgId = managersService.getMgIdByUid(statiscRequest.getUid());
        statiscRequest.setMgId(mgId);

        //统计潜在客户人数
        //统计新增潜在客户数
        int potentialUserCountResultNew = getUserTypeCountNew(statiscRequest,dateDto, EnumUserType.POTENTIAL_USER.getCode());
        int potentialUserAddCountResultNew = getUserTypeAddCountNew(statiscRequest, dateDto, EnumUserType.POTENTIAL_USER.getCode());

        //统计意向客户人数
        //统计新增意向客户人数
        int intentionalUserCountResultNew =  getUserTypeCountNew(statiscRequest,dateDto,EnumUserType.INTENTION_USER.getCode());
        int intentionalUserAddCountResultNew = getUserTypeAddCountNew(statiscRequest, dateDto, EnumUserType.INTENTION_USER.getCode());

        //统计客户人数
        //统计新增客户人数
        int customerUserCountResultNew =  getUserTypeCountNew(statiscRequest,dateDto,EnumUserType.REAL_USER.getCode());
        int customerUserAddCountResultNew = getUserTypeAddCountNew(statiscRequest, dateDto, EnumUserType.REAL_USER.getCode());

        //统计(累计)聊天人数
        //统计新增聊天人数
        int chatCountResult = getChatCount(statiscRequest, dateDto);
        int chatAddCountResult = getChatAddCount(statiscRequest, dateDto);

        //统计潜在客户跟进  意向客户跟进
        List<StaticsCountDto> getUserTypeFollowCountResult = getUserTypeFollowCount(statiscRequest, dateDto);
        int potentialUserFollowCountResult = 0;
        int intentionalUserFollowCountResult = 0;
        if (getUserTypeFollowCountResult == null || getUserTypeFollowCountResult.size() == 0) {
        } else {
            for (StaticsCountDto dto : getUserTypeFollowCountResult) {
                //0表示潜在客户
                if (dto.getStaticsType() == 0) {
                    potentialUserFollowCountResult = dto.getCount();//和"我"相关的潜在客户统计数
                }

                //1表示意向客户
                if (dto.getStaticsType() == 1) {
                    intentionalUserFollowCountResult = dto.getCount();//和"我"相关的意向客户统计数
                }
            }
        }

        List<StaticsCountDto> getUserTypeFollowAddCountResult = getUserTypeFollowAddCount(statiscRequest, dateDto);

        int potentialUserFollowAddCountResult = 0;//统计新增潜在客户跟进
        int intentionalUserFollowAddCountResult = 0;//统计新增意向客户跟进
        if (getUserTypeFollowAddCountResult == null || getUserTypeFollowAddCountResult.size() == 0) {

        } else {
            for (StaticsCountDto dto : getUserTypeFollowCountResult) {
                //0表示潜在客户
                if (dto.getStaticsType() == 0) {
                    potentialUserFollowAddCountResult = dto.getCount();//和"我"相关的新增潜在客户统计数
                }

                //1表示意向客户
                if (dto.getStaticsType() == 1) {
                    intentionalUserFollowAddCountResult = dto.getCount();//和"我"相关的新增潜在客户统计数
                }
            }
        }

        //统计(累计)咨询跟进数
        //统计新增咨询跟进数
        Integer followAfterChatCountResult = getFollowAfterChatCount(statiscRequest, dateDto);
        Integer followAfterChatAddCountResult = getFollowAfterChatAddCount(statiscRequest, dateDto);

        //统计(累计)订单数
        //统计新增订单数
        Integer orderCount = ordersService.getOrdersCountNew(statiscRequest,dateDto);
        Integer orderAddCount = ordersService.getOrdersAddCountNew(statiscRequest,dateDto);

        //统计(累计)商业总额
        //统计新增商业总额
        BigDecimal realPriceCount = ordersService.getRealPriceCountNew(statiscRequest,dateDto);
        BigDecimal realPriceAddCount = ordersService.getRealPriceAddCountNew(statiscRequest,dateDto);

        countDto = StaticsDto.builder()
                //统计用户相关
                .potentialUserCountResult(potentialUserCountResultNew)
                .potentialUserAddCountResult(potentialUserAddCountResultNew)

                .intentionalUserCountResult(intentionalUserCountResultNew)
                .intentionalUserAddCountResult(intentionalUserAddCountResultNew)

                .customerUserCountResult(customerUserCountResultNew)
                .customerUserAddCountResult(customerUserAddCountResultNew)

                //统计咨询数
                .chatAddCountResult(chatAddCountResult)
                .chatCountResult(chatCountResult)

                //统计跟进数
                .potentialUserFollowCountResult(potentialUserFollowCountResult)
                .potentialUserFollowAddCountResult(potentialUserFollowAddCountResult)

                .intentionalUserFollowCountResult(intentionalUserFollowCountResult)
                .intentionalUserFollowAddCountResult(intentionalUserFollowAddCountResult)

                //统计咨询跟进数
                .followAfterChatCountResult(followAfterChatCountResult)
                .followAfterChatAddCountResult(followAfterChatAddCountResult)

                //统计订单数
                .orderAddCountResult(orderAddCount)
                .orderCountResult(orderCount)

                //统计商业额
                .amountCountResult(realPriceCount)
                .amountAddCountResult(realPriceAddCount)
                .build();

        data.put("count", countDto);

        return new ResultCode(StatusCode.SUCCESS,data);
    }
}
