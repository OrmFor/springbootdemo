package com.jlkj.web.shopnew.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.dto.UpdateFollowDto;
import com.jlkj.web.shopnew.enums.EnumLogType;
import com.jlkj.web.shopnew.pojo.mongo.FollowMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.FollowMongoRequest;
import com.jlkj.web.shopnew.request.GetFollowListRequest;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IStaff;
import com.jlkj.web.shopnew.service.mongo.ICustomerBelongMongo;
import com.jlkj.web.shopnew.service.mongo.IFollowMongo;
import com.jlkj.web.shopnew.service.mongo.ILogMongo;
import com.jlkj.web.shopnew.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowMongoImpl implements IFollowMongo {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ILogMongo logMongoService;

    @Autowired
    private ICompany companyService;

    @Autowired
    private IStaff staffService;

    @Autowired
    private ICustomerBelongMongo customerBelongMongo;

    //保存跟进记录
    @Override
    public void saveFollow(FollowMongoRequest followMongoRequest) {

        //首先查询logmongo logType为chat是否存在过聊天 后续统计时需要用到
        //主要逻辑为再新增时判断之前是否有聊天  ， 并且没有跟进，此时isAfterChat字段设置为1
        Criteria c = Criteria.where("uid").is(followMongoRequest.getUid())
                .and("operationId").is(followMongoRequest.getBelongUid())
                .and("logType").is(EnumLogType.LOG_CHAT.getType())
                .and("updateTime").lte(DateUtil.getLastSecIntegralTime(new Date()))
                ;

        //某客户最新聊天时间
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(c),
                Aggregation.group("updateTime").max("updateTime").as("updateTime"),
                Aggregation.project("updateTime").andExclude("_id")

        );

        AggregationResults<LogMongo> results = mongoTemplate.aggregate(agg, Constant.LOGMONGO, LogMongo.class);
        List<LogMongo> mappedResults = results.getMappedResults();//和某客户最新的聊天记录
        followMongoRequest.setIsAfterChat(0);

        Date mongoDate = DateUtil.getMongoDate();//new Date
        if( mappedResults != null && mappedResults.size() != 0){//当前时间节点之前有过聊天
            //查询Follow表是否有跟进
            LogMongo logMongo = mappedResults.get(0);
            if(logMongo != null) {
                Criteria follow = Criteria.where("uid").is(followMongoRequest.getUid())
                        .and("belongUid").is(followMongoRequest.getBelongUid())
                        .and("updateTime").gte(logMongo.getUpdateTime());//和该客户聊天的最近一次时间

                Query query = Query.query(follow);
                boolean exists = this.mongoTemplate.exists(query, Constant.FOLLOWMONGO);//最近聊天时间之前是否跟进过
                if (!exists) {
                    followMongoRequest.setIsAfterChat(1);//没有跟进过标识为咨询跟进
                }
            }
        }

        followMongoRequest.setCreateTime(mongoDate);//new Date
        followMongoRequest.setUpdateTime(mongoDate);//new Date
        followMongoRequest.setTransfer(0);//转移标识

        this.mongoTemplate.save(followMongoRequest,Constant.FOLLOWMONGO);//先添加跟进记录

        List<Integer> list = staffService.getStaffUids(followMongoRequest.getBelongUid());//获取员工列表或自己
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("uid").is(followMongoRequest.getUid()),
                Criteria.where("belongUid").in(list));
        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query, FollowMongo.class);//再统计和"我"相关的该客户跟进记录总数

        //TODO 此处可以用kafka优化
        //更新LogMongo表
        UpdateFollowDto dto = UpdateFollowDto.builder()
                .belongUid(followMongoRequest.getBelongUid())
                .uid(followMongoRequest.getUid()).lastFollowTime(followMongoRequest.getUpdateTime())
                .count(count).build();
        logMongoService.updateFollow(dto);//添加跟进后更新logmongo
    }

    //获取客户相关跟进记录列表
    @Override
    public Map<String, Object> getFollowList(GetFollowListRequest getFollowListRequest) {

        List<Integer> listStaff = staffService.getStaffUids(getFollowListRequest.getBossId());//获取企业所有员工
        Criteria criteria = new Criteria();

        criteria.andOperator(
                Criteria.where("belongUid").in(listStaff),
                Criteria.where("uid").is(getFollowListRequest.getUid()));


        int page = getFollowListRequest.getPage() == 0 ? 1 : getFollowListRequest.getPage();
        int pageSize = getFollowListRequest.getPageSize() == 0 ? 10 : getFollowListRequest.getPageSize();


        PageRequest pageRequest = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "updateTime"));

        // 设置查询条件，分页
        Query query = Query.query(criteria).with(pageRequest);
        Long count = mongoTemplate.count(query, FollowMongo.class);//查询分页总记录数
        List<FollowMongo> list = this.mongoTemplate.find(query, FollowMongo.class);//分页

        Map<String,Object> data = new HashMap<>();
        data.put("totalCount",count);
        data.put("list",list);
        return data;
    }

    //获取归属用户相关的客户跟进总数
    @Override
    public int getFollowCount(int uid, DateDto dateDto) {
        Criteria criteria = new Criteria();

        criteria.andOperator(
                Criteria.where("belongUid").is(uid));
        criteria = criteria.and("updateTime").gte(
                DateUtil.getFirstSecIntegralTime(dateDto.getStartDay())
        ).lte(
                DateUtil.getLastSecIntegralTime(dateDto.getEndDay())
        );
        Query query = Query.query(criteria);
        Long count = mongoTemplate.count(query, FollowMongo.class);//查询总记录数
        return count.intValue();
    }
}
