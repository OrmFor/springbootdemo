package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.dto.StaticsChartCountDto;
import com.jlkj.web.shopnew.request.GetChartDataRequest;
import com.jlkj.web.shopnew.service.IChart;
import com.jlkj.web.shopnew.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartImpl implements IChart {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<StaticsChartCountDto> getChartData(GetChartDataRequest getChartDataRequest) {
        DateDto dateMap =DateUtil.getDayByType4Chart(getChartDataRequest.getDateType(),
                getChartDataRequest.getStartDay(),getChartDataRequest.getEndDay());

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("operationId").is(getChartDataRequest.getOperationId()),
                Criteria.where("logType").is(getChartDataRequest.getLogType()),
                Criteria.where("updateTime").lte(dateMap.getEndDay()),
                Criteria.where("updateTime").gte(dateMap.getStartDay())
        );

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.project()
                        .and(DateOperators.DateToString.dateOf("updateTime")
                .toString("%Y-%m-%d")).as("date"),
                Aggregation.group("date").count().as("count")
                .first("date").as("date"),
                Aggregation.sort(Sort.Direction.ASC,"date"),
                Aggregation.project("count","date").andExclude("_id")
        );

        AggregationResults<StaticsChartCountDto> results =
                this.mongoTemplate.aggregate(agg,Constant.LOGMONGO,StaticsChartCountDto.class);

        List<StaticsChartCountDto> mappedResults = results.getMappedResults();
        return mappedResults;
    }
}
