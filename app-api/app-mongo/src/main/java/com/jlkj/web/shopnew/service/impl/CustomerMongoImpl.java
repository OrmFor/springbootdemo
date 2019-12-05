package com.jlkj.web.shopnew.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantDateType;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.pojo.mongo.CustomerBelongMongo;
import com.jlkj.web.shopnew.pojo.mongo.CustomerMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.GetStaffSortRequest;
import com.jlkj.web.shopnew.service.mongo.ICustomerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMongoImpl implements ICustomerMongo {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveCustomerBelong(JSONObject json) {

    }

    @Override
    public int getCustomerCount(int uid, DateDto dateDto) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("customerTime").lte(dateDto.getEndDay()),
                Criteria.where("customerTime").gte(dateDto.getStartDay())
        );
        criteria.and("belongUid").is(uid);
       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );*/

        long count = mongoTemplate.count(Query.query(criteria),CustomerBelongMongo.class);

        return (int) count;/*
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.CUSTOMERBELONGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }
}
