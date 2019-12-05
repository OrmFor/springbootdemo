package com.jlkj.web.shopnew.controller.mongo;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.mongo.BrowseRecord;
import com.jlkj.web.shopnew.pojo.mongo.LogMongo;
import com.jlkj.web.shopnew.request.GetMyFollowStatiscRequest;
import com.jlkj.web.shopnew.request.StatiscRequest;
import com.jlkj.web.shopnew.service.mongo.ILogMongo;
import com.jlkj.web.shopnew.utils.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/1.0")
public class StatisticsController extends BaseController {
    private static final Logger LOGGER = LogManager.getLogger(StatisticsController.class);

    @Autowired
    private ILogMongo logMongoService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @Deprecated
    public ResultCode getList(@RequestBody JSONObject json){
        String visitedCode = json.getString("visitedCode");
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("visitedCode").is(visitedCode));
        GroupBy groupBy = GroupBy.key("type","visitedCode")
                .initialDocument("{total:0}")
                .reduceFunction(
                        "function(doc, prev)" +
                                "{ prev.total += parseFloat(doc.num);}")
                ;
        GroupByResults<BrowseRecord> res = mongoTemplate.group(criteria,"BrowseRecord",
                groupBy, BrowseRecord.class);
        Document obj = res.getRawResults();
        LOGGER.info(obj);
        List dbList = (List) obj.get("retval");

        return new ResultCode(StatusCode.SUCCESS,dbList);
    }


    //用户行为统计
    @RequestMapping(value = "/statistic", method = RequestMethod.POST)
    @Deprecated
    public ResultCode statistic(@RequestBody JSONObject json){
        int operationId = json.getInteger("operationId");
        int type = json.getInteger("type");
        Criteria criteria =new Criteria().andOperator(Criteria.where("operationId").is(operationId));
        //默认表示 累计 时间转换   1.表示 昨日  2.表示自定义筛选
        switch (type){
            case 1:
                Date yesterday =  DateUtil.rollDay(new Date(), -10);
                System.out.println(DateUtil.getLastSecIntegralTime(yesterday));
                System.out.println(DateUtil.getFirstSecIntegralTime(yesterday));
                criteria =   criteria.and("updateTime").lte(DateUtil.getLastSecIntegralTime(yesterday)).
                        gte(DateUtil.getFirstSecIntegralTime(yesterday));
                break;
            case 2:
                Date startDay = json.getDate("startDay");
                Date endDay = json.getDate("endDay");
                criteria =  criteria.and("updateTime").gte(DateUtil.getFirstSecIntegralTime(startDay))
                        .lte(DateUtil.getLastSecIntegralTime(endDay))
                       ;
                break;
            default:
                    break;
        }
        GroupBy groupBy = GroupBy.key("logType","operationId")
                .initialDocument("{total:0}")
                .reduceFunction(
                        "function(doc, prev)" +
                                "{ prev.total += NumberLong(doc.num);" +
                                " }")
                ;
        GroupByResults<LogMongo> res = mongoTemplate.group(criteria,Constant.LOGMONGO,
                groupBy, LogMongo.class);
        Document obj = res.getRawResults();
        LOGGER.info(obj);
        List dbList = (List) obj.get("retval");
        return new ResultCode(StatusCode.SUCCESS,dbList);
    }

    //我的跟进统计
    @RequestMapping(value = "/getMyFollowStatics", method = RequestMethod.POST)
    public ResultCode getMyFollowStatics(@RequestBody GetMyFollowStatiscRequest getMyFollowStatiscRequest){
        Map<String,Object> data = logMongoService.getMyFollowStatics(getMyFollowStatiscRequest);//我的跟进等统计信息
        return new ResultCode(StatusCode.SUCCESS,data);
    }


    /**
    * @Author wwy
    * @Description BOSS雷达 和 我的跟进
     *                isBoss = true Boss雷达
     *                isBoss = false 我的跟进
    * @Date 11:08 2019/9/25
    * @Param [statiscRequest]
    * @return com.jlkj.web.shopnew.core.ResultCode
    **/
    @RequestMapping(value = "/getStatics", method = RequestMethod.POST)
    public ResultCode getStatics(@RequestBody StatiscRequest statiscRequest){
        return logMongoService.getStaticsNew(statiscRequest);
    }


}
