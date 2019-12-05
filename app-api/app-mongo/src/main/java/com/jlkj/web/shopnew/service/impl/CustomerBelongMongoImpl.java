package com.jlkj.web.shopnew.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantDateType;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.dto.DistributeDto;
import com.jlkj.web.shopnew.enums.EnumUserType;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.pojo.mongo.CustomerBelongMongo;
import com.jlkj.web.shopnew.pojo.mongo.FollowMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.*;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IStaff;
import com.jlkj.web.shopnew.service.IUser;
import com.jlkj.web.shopnew.service.mongo.ICustomerBelongMongo;
import com.jlkj.web.shopnew.util.DateUtil;
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

import java.util.*;


@Service
public class CustomerBelongMongoImpl implements ICustomerBelongMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IUser userService;

    @Autowired
    private IStaff staffService;

    @Autowired
    private ICompany companyService;

    private static final Logger logger = LogManager.getLogger(CustomerBelongMongoImpl.class);

    //保存客户归属记录
    @Override
    public void saveCustomerBelong(CustomerBelongMongo bean) {
        //修改名片  //修改客户类型
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(bean.getUid()),
                Criteria.where("belongUid").is(bean.getBelongUid())
        );

        Query query = Query.query(criteria);
        CustomerBelongMongo customerBelongMongo = this.mongoTemplate.findOne(query,
                CustomerBelongMongo.class);
        Date date = DateUtil.getMongoDate();//new Date
        if (customerBelongMongo != null) {
            Update update = new Update();

            if (bean.getUserType() == 0) {
                update.set("potentialTime", date);
                update.set("potentialType", 1);
                update.set("userType", 0);

            } else if (bean.getUserType() == 1) {
                update.set("intentionalTime", date);
                update.set("intentionalType", 1);
                update.set("userType", 1);

            } else if (bean.getUserType() == 2) {
                update.set("customerTime", date);
                update.set("userType", 2);
            }

            if (bean.getLabel() != null) {
                update.set("label", bean.getLabel());
            }
            update.set("updateTime", date);

            this.mongoTemplate.updateMulti(query, update, CustomerBelongMongo.class);

        } else {

            try {
                if (bean.getUserType() == 0) {
                    bean.setPotentialTime(date);
                    bean.setPotentialType(1);
                    bean.setUserType(0);
                } else if (bean.getUserType() == 1) {
                    bean.setIntentionalTime(date);
                    bean.setIntentionalType(1);
                    bean.setUserType(1);
                } else if (bean.getUserType() == 2) {
                    bean.setCustomerTime(date);
                    bean.setUserType(2);
                }
                User condi = new User();
                condi.setId(bean.getUid());
                User user = userService.getByCondition(condi);//获取客户信息
                bean.setUserName(user == null ? "" : user.getUsername());
                bean.setPhone(user == null ? "" : user.getPhone());//设置客户联系电话

                User cond = new User();//获取归属用户信息
                cond.setId(bean.getBelongUid());
                user = userService.getByCondition(cond);

                bean.setBelongUserName(user == null ? "" : user.getUsername());
                bean.setCreateTime(new Date());
                bean.setUpdateTime(new Date());
                bean.setTransfer(0);//转移标识
                mongoTemplate.save(bean, Constant.CUSTOMERBELONGMONGO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //更新客户相关信息(归属,足迹)
    @Override
    public void modifyCustomer(ModifyCustomerRequest request) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(request.getUid()),
                Criteria.where("belongUid").is(request.getBelongUid())
        );
        Date date = DateUtil.getMongoDate();//new Date
        Query query = Query.query(criteria);
        CustomerBelongMongo bean = this.mongoTemplate.findOne(query, CustomerBelongMongo.class);
        if (bean != null) {
            Update update = new Update();
            if (request.getUserType() == 0) {
                update.set("potentialTime", date);
                update.set("potentialType", 1);
                update.set("userType", 0);

            } else if (request.getUserType() == 1) {
                update.set("intentionalTime", date);
                update.set("intentionalType", 1);
                update.set("userType", 1);

            } else if (request.getUserType() == 2) {
                update.set("customerTime", date);
                update.set("userType", 2);
            }
            this.mongoTemplate.updateMulti(query, update, CustomerBelongMongo.class);//更新客户归属记录
            this.saveLogMongo(request);//更新足迹
        }
        //修改follow
        //获取用户类型
       /* GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest();
        getUserInfoRequest.setOperationId(request.getBelongUid());
        getUserInfoRequest.setUid(request.getUid());
        int userType = this.getUserType(getUserInfoRequest);
        Criteria follow = new Criteria().andOperator(
                Criteria.where("uid").is(request.getUid()),
                Criteria.where("belongUid").is(request.getBelongUid())
        );

        Query queryFollow = Query.query(criteria);
        FollowMongo followMongo = this.mongoTemplate.findOne(queryFollow, FollowMongo.class);
        if (bean != null) {
            Update update = Update.update("userType", request.getUserType());
            this.mongoTemplate.updateFirst(query, update, FollowMongo.class);

        }
*/

    }

    private void saveLogMongo(ModifyCustomerRequest request){
        LogMongo logMongo = new LogMongo();
        int companyId = companyService.getCompanyId(request.getBelongUid());
        if (companyId != -1){
            logMongo.setCompanyId(companyId);
        }

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(request.getBelongUid()),
                Criteria.where("uid").is(request.getUid()),
                Criteria.where("logType").is(5));//5是人脉浏览
        Query query = Query.query(criteria);
       // query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        query.with( Sort.by(Sort.Direction.DESC,"id"));

        LogMongo bean = this.mongoTemplate.findOne(query, LogMongo.class);
        if (bean != null) {
            long num = bean.getNum();
            logMongo.setNum(num + 1L);
        } else {
            logMongo.setNum(1L);
        }

        User user = userService.selectByPrimaryKey(request.getUid());
        User belongUser = userService.selectByPrimaryKey(request.getBelongUid());

        logMongo.setCreateTime(DateUtil.getMongoDate());
        logMongo.setUpdateTime(DateUtil.getMongoDate());
        logMongo.setUserType(request.getUserType());
        logMongo.setLogType(5);
        logMongo.setUid(request.getUid());
        logMongo.setUserName(user == null ? "" : user.getUsername());// 5是浏览人脉 获取的是name 不是微信名
        logMongo.setUserImg(user == null ? "" : user.getLogo());
        logMongo.setOperationId(request.getBelongUid());
        logMongo.setOperationName(belongUser == null ? "" : belongUser.getName());
        logMongo.setTransfer(0);//转移标识
        this.mongoTemplate.save(logMongo, Constant.LOGMONGO);
    }

    //修改客户标签
    @Override
    public void modifyCustomerLabel(ModifyCustomerLabel request) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(request.getUid()),
                Criteria.where("belongUid").is(request.getBelongUid())
        );

        Query query = Query.query(criteria);
        CustomerBelongMongo bean = this.mongoTemplate.findOne(query, CustomerBelongMongo.class);
        if (bean != null) {
            Update update = Update.update("label", request.getLabel());
            this.mongoTemplate.updateMulti(query, update, CustomerBelongMongo.class);

        }
    }

    //获取归属用户的某客户的类型
    @Override
    public int getUserType(GetUserInfoRequest getUserInfoRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(getUserInfoRequest.getUid()),
                Criteria.where("belongUid").is(getUserInfoRequest.getOperationId())
        );

        Query query = Query.query(criteria);
        CustomerBelongMongo bean = this.mongoTemplate.findOne(query, CustomerBelongMongo.class);
        if (bean != null) {
            if (bean.getUserType() == EnumUserType.REAL_USER.getCode()) {
                return EnumUserType.REAL_USER.getCode();
            } else if (bean.getIntentionalType() == 1
                    && bean.getUserType() == EnumUserType.INTENTION_USER.getCode()) {
                return EnumUserType.INTENTION_USER.getCode();
            } else if (bean.getPotentialType() == 1
                    && bean.getUserType() ==  EnumUserType.POTENTIAL_USER.getCode()) {
                return EnumUserType.POTENTIAL_USER.getCode();
            }

        }

        return EnumUserType.POTENTIAL_USER.getCode();
    }

    //获得客户标签
    @Override
    public String getUserLabel(GetUserInfoRequest getUserInfoRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(getUserInfoRequest.getUid()),
                Criteria.where("belongUid").is(getUserInfoRequest.getOperationId())
        );

        Query query = Query.query(criteria);
        CustomerBelongMongo bean = this.mongoTemplate.findOne(query, CustomerBelongMongo.class);
        return bean == null ? "" : bean.getLabel();
    }

    //获取某类型的客户数据
    @Override
    public Map<String, Object> getAllUserByType(GetAllUserByTypeRequest getAllUserByTypeRequest) {
        DateDto dateDto = DateUtil.getDayByType(getAllUserByTypeRequest.getDateType(),
                getAllUserByTypeRequest.getStartDay(), getAllUserByTypeRequest.getEndDay());

        /*String timeType = EnumTimeType.POTENTIALTIME.getTimeType();

        if (getAllUserByTypeRequest.getUserType() == 0) {
            timeType = EnumTimeType.POTENTIALTIME.getTimeType();
        } else if (getAllUserByTypeRequest.getUserType() == 1) {
            timeType = EnumTimeType.INTENTIONALTIME.getTimeType();
        } else if (getAllUserByTypeRequest.getUserType() == 2) {
            timeType = EnumTimeType.CUSTOMERTIME.getTimeType();
        }


        Criteria criteria = new Criteria();
        if (ConstantDateType.ALL_DAY.equals(getAllUserByTypeRequest.getDateType())) {
            criteria.and(timeType).lte(dateDto.getEndDay());
        } else {
            criteria.and(timeType).lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        if (getAllUserByTypeRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(getAllUserByTypeRequest.getUid());
            criteria.and("belongUid").in(list);
        } else {
            criteria.and("belongUid").is(getAllUserByTypeRequest.getUid());
        }

        //获取总人数
        long count = mongoTemplate.count(Query.query(criteria), CustomerBelongMongo.class);
*/
        int page = getAllUserByTypeRequest.getPage() == 0 ? 1 : getAllUserByTypeRequest.getPage();
        int pageSize = getAllUserByTypeRequest.getPageSize() == 0 ? 10 : getAllUserByTypeRequest.getPageSize();

        Criteria criteria1 = new Criteria();
        if (ConstantDateType.ALL_DAY.equals(getAllUserByTypeRequest.getDateType())) {//累计
            criteria1.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria1.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        if (getAllUserByTypeRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(getAllUserByTypeRequest.getUid());
            criteria1.and("operationId").in(list);//boss的客户
        } else {
            criteria1.and("operationId").is(getAllUserByTypeRequest.getUid());//staff的客户
        }

        criteria1.and("userType").is(getAllUserByTypeRequest.getUserType());


        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "createTime"));
        Query query = new Query(criteria1);
        //获取总客户数
        long totalCount = mongoTemplate.count(query, LogMongo.class);
        query.with(pageRequest);
        ///获取足迹
        List<LogMongo> list = mongoTemplate.find(query, LogMongo.class);

        Map<String, Object> data = Maps.newHashMap();
        //data.put("count", count);
        data.put("list", list);
        data.put("totalCount", totalCount);

        return data;
    }

    //获取某类型的客户各行为数据 聚合 新
    public Map<String, Object> getAllUserByTypeNew(GetAllUserByTypeRequest getAllUserByTypeRequest) {
        DateDto dateDto = DateUtil.getDayByType(getAllUserByTypeRequest.getDateType(),
                getAllUserByTypeRequest.getStartDay(), getAllUserByTypeRequest.getEndDay());

        int page = getAllUserByTypeRequest.getPage() == 0 ? 1 : getAllUserByTypeRequest.getPage();
        int pageSize = getAllUserByTypeRequest.getPageSize() == 0 ? 10 : getAllUserByTypeRequest.getPageSize();
        int totalCount=0;
        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria();
        if (ConstantDateType.ALL_DAY.equals(getAllUserByTypeRequest.getDateType())) {//累计
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        if (getAllUserByTypeRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(getAllUserByTypeRequest.getUid());
            criteria.and("operationId").in(list);//boss的客户
        } else {
            criteria.and("operationId").is(getAllUserByTypeRequest.getUid());//staff的客户
        }

        criteria.and("userType").is(getAllUserByTypeRequest.getUserType());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        if(results==null||results.getMappedResults()==null||results.getMappedResults().size()==0){
            map.put("totalCount",totalCount);
            map.put("list", Lists.newArrayList());
            return map;
        }
        totalCount=results.getMappedResults().size();

        agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC,"updateTime"),
                Aggregation.group("uid","logType").max("updateTime").as("updateTime")
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

        ///获取足迹
        results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();
        map.put("list", mappedResults);
        map.put("totalCount",totalCount);

        return map;
    }

    //获取指定员工的客户记录数量
    @Override
    public int getDelStaffCustomerCount(DelStaffInfoRequest delStaffInfoRequest) {
        Criteria criteria = new Criteria();
        criteria.and("belongUid").is(delStaffInfoRequest.getStaffId());

        Query query = new Query(criteria);
        List<CustomerBelongMongo> list = mongoTemplate.findDistinct(query, "uid", "CustomerBelongMongo", CustomerBelongMongo.class);
        int totalCount=list.size();

        return totalCount;
    }

    //分配更新customerBelongMongo followMongo logMongo
    @Override
    public void distributeUpdate(int originalStaffId, DistributeDto distributeDto) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(originalStaffId)
        );

        Query query = new Query(criteria).limit(distributeDto.getCount());

        List<CustomerBelongMongo> list=mongoTemplate.find(query, CustomerBelongMongo.class);
        List<Integer> uidList=new ArrayList<>();

        for(CustomerBelongMongo customerBelongMongo:list){
            uidList.add(customerBelongMongo.getUid());
        }

        distributeCustomerMongo(originalStaffId,distributeDto,uidList);             //第一步 更新customerMongo
        distributeFollowMongo(originalStaffId,distributeDto.getStaffId(),uidList);  //第二步 更新followMongo
        distributeLogMongo(originalStaffId,distributeDto.getStaffId(),uidList);     //第三步 更新logMongo
    }

    //分配归属记录
    public void distributeCustomerMongo(int originalStaffId, DistributeDto distributeDto, List<Integer> uidList) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(originalStaffId)
        );

        Query queryNew = new Query(criteria.and("uid").in(uidList));

        Update update=new Update();
        update.set("belongUid",distributeDto.getStaffId());
        update.set("transfer",1);
        mongoTemplate.updateMulti(queryNew, update, CustomerBelongMongo.class);
    }

    //分配跟进记录
    public void distributeFollowMongo(int originalStaffId, int currentStaffId,List<Integer> uidList) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("belongUid").is(originalStaffId)
        );

        criteria.and("uid").in(uidList);

        Query query = new Query(criteria);
        Update update=new Update();
        update.set("belongUid",currentStaffId);
        update.set("transfer",1);//标识转移
        mongoTemplate.updateMulti(query,update,FollowMongo.class);
    }

    //分配足迹记录
    public void distributeLogMongo(int originalStaffId, int currentStaffId,List<Integer> uidList) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(originalStaffId)
        );

        criteria.and("uid").in(uidList);

        Query query = new Query(criteria);
        Update update=new Update();
        update.set("operationId",currentStaffId);
        update.set("transfer",1);//标识转移
        mongoTemplate.updateMulti(query,update,LogMongo.class);
    }
}
