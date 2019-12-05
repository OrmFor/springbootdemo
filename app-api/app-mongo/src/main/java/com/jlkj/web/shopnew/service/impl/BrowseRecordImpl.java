package com.jlkj.web.shopnew.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.pojo.mongo.BrowseRecord;
import com.jlkj.web.shopnew.request.UpdateCustomerRequest;
import com.jlkj.web.shopnew.service.mongo.IBrowseRecord;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @Author wwy
* @Description mongodb 增删查改demo
* @Date 10:12 2019/9/16
* @Param  
* @return 
**/
@Service
public class BrowseRecordImpl implements IBrowseRecord {
    private static final Logger LOGGER = LogManager.getLogger(BrowseRecordImpl.class);


    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Map<String,Object> findList(String visited, Integer page , int rows) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("visited").is(visited)
        );

        PageRequest pageRequest = PageRequest.of(page - 1, rows,
                Sort.by(Sort.Direction.ASC, "time"));

        // 设置查询条件，分页
        Query query = Query.query(criteria).with(pageRequest);
        Long count = mongoTemplate.count(query, BrowseRecord.class);//查询总记录数
        List<BrowseRecord> list = this.mongoTemplate.find(query, BrowseRecord.class);

        Map<String,Object> data = new HashMap<>();
        data.put("totalCount",count);
        data.put("list",list);
        return data;
    }

    @Override
    public BrowseRecord findBrowseRecordByVisitor(String visitor) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("visited").is(visitor)
        );
        Query query = Query.query(criteria);
        query.fields().exclude("id");
        return this.mongoTemplate.findOne(query, BrowseRecord.class);

    }

    @Override
    public UpdateResult updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("visitorCode").is(updateCustomerRequest.getVisitorCode()),
                Criteria.where("visitedCode").is(updateCustomerRequest.getVisitedCode()));
        Query query = Query.query(criteria);
        BrowseRecord bean = this.mongoTemplate.findOne(query, BrowseRecord.class);

        if(bean != null){
            Update update = Update.update("type",updateCustomerRequest.getType());
            return this.mongoTemplate.updateMulti(query, update , BrowseRecord.class);
        }
        return null;
    }

    @Override
    public UpdateResult update(String visitor) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("visitorCode").is(visitor));
        Query query = Query.query(criteria);
        BrowseRecord bean = this.mongoTemplate.findOne(query, BrowseRecord.class);

        String num = bean.getNum();
        Update update = Update.update("num", String.valueOf(Long.valueOf(num + 1)));
        return  this.mongoTemplate.updateFirst(query, update, BrowseRecord.class);
    }

    @Override
    public void saveBrowseRecord(BrowseRecord browseRecord) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("visitorCode").is(browseRecord.getVisitorCode()),
                Criteria.where("visitedCode").is(browseRecord.getVisitedCode()));
        Query query = Query.query(criteria);
        BrowseRecord bean = this.mongoTemplate.findOne(query, BrowseRecord.class);
        if (bean != null) {
            String num = bean.getNum();
            Update update = Update.update("num", String.valueOf(Long.valueOf(num) + 1L));
            this.mongoTemplate.updateFirst(query, update, BrowseRecord.class);
        } else {
            browseRecord.setNum(String.valueOf(1L));
            this.mongoTemplate.save(browseRecord);
        }
    }

    @Override
    public DeleteResult delete(String id) {
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        return this.mongoTemplate.remove(query,BrowseRecord.class);
    }

    @Override
    public Map<String, Object> count(String visitedCode , String visitorCode) {
        LOGGER.info(MessageFormat.format("visitedCode={0},visitorCode={1}",visitedCode,visitorCode));

        Criteria criteria = new Criteria().andOperator(
               // Criteria.where("visitorCode").is(visitorCode),
                Criteria.where("visitedCode").is(visitedCode));


        Aggregation agg = Aggregation.newAggregation(
                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("num", "time","type"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("type").sum("num").as("num"),
                // 第四部：排序（根据某字段排序 倒序）
             //   Aggregation.sort(Sort.Direction.DESC,"time"),
                // 第五步：数量(分页)
               Aggregation.limit(10),
                // 第刘步：重新挑选字段
                Aggregation.project("num")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "BrowseRecord", JSONObject.class);
        LOGGER.info(results);
        List<JSONObject> mappedResults = results.getMappedResults();

        LOGGER.info(mappedResults);
        Map<String , Object> data = new HashMap<>();
        data.put("count",results);

        return data;

       /* List ob = mongoGroup(visitedCode);
        LOGGER.info(ob);
        Map<String , Object> data = new HashMap<>();

        data.put("data",ob);
        return data;*/

    }



//db.runCommand({"group":{
//    "ns":"BrowseRecord",
//    "key":"type",
//    "initial":{"total":0},
//    "$reduce" : function(doc,prev){
//        prev.total += parseFloat(doc.num);
//    },
//    "condition":{"visitedCode":"1"}}
//});
    public List mongoGroup(String visitedCode ) {
        Criteria criteria = new Criteria().andOperator(
                // Criteria.where("visitorCode").is(visitorCode),
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
        return dbList;
    }
}
