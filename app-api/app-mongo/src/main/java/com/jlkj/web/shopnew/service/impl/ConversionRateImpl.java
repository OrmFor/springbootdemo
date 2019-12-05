package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.constant.ConstantDateType;
import com.jlkj.web.shopnew.dto.ConversionRateDto;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.pojo.mongo.CustomerBelongMongo;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.GetConversionRateRequest;
import com.jlkj.web.shopnew.request.GetStaffSortRequest;
import com.jlkj.web.shopnew.request.StatiscRequest;
import com.jlkj.web.shopnew.service.IConversionRate;
import com.jlkj.web.shopnew.service.IStaff;
import com.jlkj.web.shopnew.service.mongo.ILogMongo;
import com.jlkj.web.shopnew.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class ConversionRateImpl implements IConversionRate {

    @Autowired
    private IStaff staffService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ILogMongo logMongo;

    //获得转换率
    @Override
    public ConversionRateDto getConversionRate(StatiscRequest statiscRequest) {
        DateDto dateDto = DateUtil.getDayByType(statiscRequest.getDateType()
                ,statiscRequest.getStartDay(),
                statiscRequest.getEndDay());
        int potentialUserCountResult = logMongo.getUserTypeCountNew(statiscRequest,dateDto,0);//统计潜在客户数
        int intentionalUserCountResult = logMongo.getUserTypeCountNew(statiscRequest,dateDto,1);//统计意向客户数
        int customerUserCountResult = logMongo.getUserTypeCountNew(statiscRequest,dateDto,2);//统计真实客户数

        /*
        int potentialUserCountResult = logMongo.getUserTypeCount(statiscRequest, dateDto,"potentialTime" );
        int intentionalUserCountResult = logMongo.getUserTypeCount(statiscRequest, dateDto,"intentionalTime" );
        int customerUserCountResult = logMongo.getUserTypeCount(statiscRequest, dateDto,"customerTime" );*/
        BigDecimal pToi = BigDecimal.ZERO;//意向客户转换率
        BigDecimal iToc = BigDecimal.ZERO;//真实客户转换率
        if(potentialUserCountResult != 0) {
            pToi  = new BigDecimal(intentionalUserCountResult).
                    divide(new BigDecimal(potentialUserCountResult) , 2 , RoundingMode.HALF_UP);
        }
        if(intentionalUserCountResult != 0) {
            iToc = new BigDecimal(customerUserCountResult).
                    divide(new BigDecimal(intentionalUserCountResult) , 2 ,RoundingMode.HALF_UP);
        }

       return ConversionRateDto.builder().potentialUserCountResult(potentialUserCountResult)
                .intentionalUserCountResult(intentionalUserCountResult)
                .customerUserCountResult(customerUserCountResult)
                .pToi(pToi)
                .iToc(iToc).build();
    }

    //intentionalTime customerTime potentialTime
    public Integer getAllOfCustomer(GetConversionRateRequest getConversionRateRequest,
                                     String timeType){
        DateDto dateDto = DateUtil.getDayByType(getConversionRateRequest.getDateType()
                ,getConversionRateRequest.getStartDay(),
                getConversionRateRequest.getEndDay());

        Criteria criteria = new Criteria();

        if (ConstantDateType.ALL_DAY.equals(getConversionRateRequest.getDateType())) {//累计 一端
            criteria.and(timeType).lte(dateDto.getEndDay());
        } else {//非累计 两端
            criteria.andOperator(
                    Criteria.where(timeType).lte(dateDto.getEndDay()),
                    Criteria.where(timeType).gte(dateDto.getStartDay())
            );
        }

        if(getConversionRateRequest.isBoss()){//统计公司的
            List<Integer> list = staffService.getCompanyStaff(getConversionRateRequest.getUid());
            criteria.and("belongUid").in(list);
        }else {//统计普通员工个人的
            criteria.and("belongUid").is(getConversionRateRequest.getUid());
        }

        long count = mongoTemplate.count(Query.query(criteria),CustomerBelongMongo.class);

        return (int) count;
       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.CUSTOMERBELONGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }

    @Override
    public int getCustomerByType(GetStaffSortRequest getStaffSortRequest, DateDto dateDto,
                                 String timeType) {
        Criteria criteria = new Criteria();

        if (ConstantDateType.ALL_DAY.equals(getStaffSortRequest.getDateType())) {
            criteria.and(timeType).lte(dateDto.getEndDay());
        } else {
            criteria.andOperator(
                    Criteria.where(timeType).lte(dateDto.getEndDay()),
                    Criteria.where(timeType).gte(dateDto.getStartDay())
            );
        }

        criteria.and("uid").is(getStaffSortRequest.getUid());

        long count = mongoTemplate.count(Query.query(criteria),CustomerBelongMongo.class);

        return (int) count;

       /* Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
                Aggregation.group().count().as("num"),
                Aggregation.project("num").andExclude("_id")
        );
        AggregationResults<Integer> results = mongoTemplate.aggregate(agg, Constant.CUSTOMERBELONGMONGO, Integer.class);
        List<Integer> mappedResults = results.getMappedResults();
        return (mappedResults == null || mappedResults.size() == 0) ? 0 : mappedResults.get(0);*/
    }
    //按客户类型统计(请求对象,处理后的日期时间,客户类型)
    @Override
    public int getCustomerByType(GetStaffSortRequest getStaffSortRequest, DateDto dateDto, int userType) {
        Criteria criteria = new Criteria();
        if (ConstantDateType.ALL_DAY.equals(getStaffSortRequest.getDateType())) {
            criteria.and("updateTime").lte(dateDto.getEndDay());
        } else {
            criteria.and("updateTime").lte(dateDto.getEndDay()).gte(dateDto.getStartDay());
        }

        criteria.and("userType").is(userType);//2 真实客户
        List<Integer> list = staffService.getCompanyStaff(getStaffSortRequest.getUid());//返回自己或企业的所有员工
        criteria.and("operationId").in(list);

        Aggregation agg = Aggregation.newAggregation(
                // 第一步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第二步：分组条件，设置分组字段
//                Aggregation.group("uid").count().as("num")
//                        .first("userType").as("userType"),
//                Aggregation.project("num").andExclude("_id")
                Aggregation.count().as("num"),
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

}
