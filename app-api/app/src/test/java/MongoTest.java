import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.APPApplication;
import com.jlkj.web.shopnew.constant.Constant;
import com.mongodb.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = APPApplication.class)
@WebAppConfiguration
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main1(String[] args) {
        Mongo m = new Mongo("localhost:27017");
        DB db = m.getDB("test");//test为数据库的名称
        DBCollection coll = db.getCollection("BrowseRecord");//test为集合名称
//下面则就行构造管道中操作节点的操作符,主要用到的对象就是DBObject
        DBObject match = new BasicDBObject("$match", new BasicDBObject("visitedCode", "22"));//限定查询条件，相当于Query,规定某个字段的值进行groupby
        //DBObject groupFields = new BasicDBObject(“_id”,”$字段名”);//也就是说groupby这个字段名
        DBObject groupFields = new BasicDBObject("_id", "$type");
        groupFields.put("SumElectricty", new BasicDBObject("$sum", "$num"));//对这个字段名的值进行求和，并且把这个和值生成一个名为SumElectricty的字段。
        DBObject group = new BasicDBObject("$group", groupFields);
//放到管道中将这些节点运算符运算起来
        AggregationOutput output = coll.aggregate(match, group);
//AggregationOutput 类有getCommandResult(),返回运行结果，结果是CommandResult,可以查看到。
        output.results();
        System.out.println(output.results());

    }



    @Test
    public void test() {
        Criteria criteria = new Criteria().andOperator(
                // Criteria.where("visitorCode").is(visitorCode),
                Criteria.where("visitedCode").is("191"));


        Aggregation agg = Aggregation.newAggregation(
                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("num", "time", "type"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(criteria),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("$type").sum("$num").as("num"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC, "$time"),
                // 第五步：数量(分页)
                Aggregation.limit(10),
                // 第刘步：重新挑选字段
                Aggregation.project("$num", "$time")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "BrowseRecord", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();

        Map<String, Object> data = new HashMap<>();
        data.put("count", results);

    }

/*
    @Test
    public void test (String[] args) {
        //list用于$or的查询条件
        ArrayList<DBObject> list = new ArrayList<DBObject>();
        BasicDBObject or = new BasicDBObject();

        or.put("date", new BasicDBObject("$gte", 1412092800000L).append("$lte", 1412611200000L));
        list.add(or);
        or.put("date", new BasicDBObject("$gte", 1393603200000L).append("$lte", 1398787200000L));
        list.add(or);

        DBObject match = new BasicDBObject();
        match.put("$match", new BasicDBObject("roomTypeId", "1232").append("$or", list));
        DBObject groupOpt = new BasicDBObject();
        // 对分组字段统计行数且分组统计字段默认为返回结果集合
        DBObject groupFields = new BasicDBObject("_id", "$rateCode");
        groupFields.put("rateCode", new BasicDBObject("$min", "$rateCode"));
        groupFields.put("avgOrgin", new BasicDBObject("$avg", "$orginPrice"));
        groupFields.put("avgPrice", new BasicDBObject("$avg", "$price"));
        groupFields.put("avgCost", new BasicDBObject("$avg", "$cost"));
        groupOpt.put("$group", groupFields);
        //设置显示的字段集合

        DBObject fields = new BasicDBObject("rateCode", 1);
        fields.put("orginPrice", 1);
        fields.put("cost", 1);
        fields.put("rateCode", 1);
        DBObject project = new BasicDBObject("$project", fields);
        AggregationOutput aggrResult = mongoTemplate.getDb().getCollection(Constant.LOGMONGO)
                .aggregate(match, project, groupOpt);
        Iterator<DBObject> iter = aggrResult.results().iterator();
        while (iter.hasNext()) {
            DBObject obj = (DBObject) iter.next();
            String rateCode = obj.get("rateCode").toString();
            int orginPrice = (int) Double.parseDouble(obj.get(
                    "avgOrgin").toString());
            int cost = (int) Double.parseDouble(obj.get(
                    "avgCost").toString());
            //map.put(rateCode, orginPrice + "/" + cost);
        }


    }*/
}
