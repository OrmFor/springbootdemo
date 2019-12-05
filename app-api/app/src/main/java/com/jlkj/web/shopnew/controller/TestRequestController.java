package com.jlkj.web.shopnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.controller.base.BaseController;


import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.mongo.BrowseRecord;
import com.jlkj.web.shopnew.pojo.Message;
import com.jlkj.web.shopnew.request.TestInsertRequest;
import com.jlkj.web.shopnew.request.UpdateCustomerRequest;
import com.jlkj.web.shopnew.service.mongo.IBrowseRecord;
import com.jlkj.web.shopnew.service.MessageService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(description = "用户接口")
@RequestMapping("/api/test")
public class TestRequestController extends BaseController {

    @Autowired
    private MessageService messageDAO;

    @Autowired
    private IBrowseRecord IBrowseRecord;

    @Autowired
    private MongoTemplate mongoTemplate;


    @ApiOperation(value="获取用户列表", notes="需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"
                    , dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条"
                    , dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "info", value = "会员名称或者电话"
                    , dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"
                    , dataType = "Long",paramType="query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"
                    , dataType = "Long",paramType="query")
    })
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public com.jlkj.web.shopnew.core.ResultCode Test(){
        List<Message> list = this.messageDAO.findListByFromAndTo(1001L, 1002L, 1,
                1);
        for (Message message : list) {
            System.out.println(message);
        }
        return new com.jlkj.web.shopnew.core.ResultCode(StatusCode.SUCCESS,list);

    }


    @RequestMapping(value = "/testInsert",method = RequestMethod.POST)
    public com.jlkj.web.shopnew.core.ResultCode testInsert(@RequestBody TestInsertRequest testInsertRequest){
        BrowseRecord bean =
        BrowseRecord.builder().visited(testInsertRequest.getVisited())
                .visitedCode(testInsertRequest.getVisitedCode())
                .visitor(testInsertRequest.getVisitor())
                .visitorCode(testInsertRequest.getVisitorCode()).time(new Date()).type(1).build();
        this.IBrowseRecord.saveBrowseRecord(bean);
        return new com.jlkj.web.shopnew.core.ResultCode(StatusCode.SUCCESS);

    }


    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public com.jlkj.web.shopnew.core.ResultCode getList(){
        JSONObject json = getJsonFromRequest();
        String userName = json.getString("userName");
        int page = json.getInteger("pageNumber");
        int pageSize = json.getInteger("pageSize");
        if(page <= 0){
            page = 1;
        }
        if(pageSize <= 0){
            pageSize = 10;
        }
        Map<String,Object> data = this.IBrowseRecord.findList(userName,page,pageSize);
        return new com.jlkj.web.shopnew.core.ResultCode(StatusCode.SUCCESS,data);
    }


    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public com.jlkj.web.shopnew.core.ResultCode delete(){
        JSONObject json = getJsonFromRequest();
        String id = json.getString("id");

        DeleteResult result = this.IBrowseRecord.delete(id);
        return new com.jlkj.web.shopnew.core.ResultCode(StatusCode.SUCCESS,result);
    }

    @RequestMapping(value = "/updateCustomer",method = RequestMethod.POST)
    public com.jlkj.web.shopnew.core.ResultCode updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest){


        UpdateResult result = this.IBrowseRecord.updateCustomer(updateCustomerRequest);
        return new com.jlkj.web.shopnew.core.ResultCode(StatusCode.SUCCESS,result);
    }


    @RequestMapping(value = "/getCount",method = RequestMethod.POST)
    public ResultCode getCount(){
        JSONObject json = getJsonFromRequest();
        String visitedCode = json.getString("visitedCode");
        String visitorCode = json.getString("visitorCode");
        Map<String,Object> data = this.IBrowseRecord.count(visitedCode,visitorCode);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

/*    public void findPartyDuesByParty(String year, String month) {
        String reduce = "function(doc, pre) " +
                "{" +
                "pre.count+=1;" +
                "var partyDueShouldBe = doc.map.partyDueShouldBe;"+
                "partyDueShouldBe = typeof(partyDueShouldBe)=='string'?0:partyDueShouldBe;"+
                "pre.sumOfPartyDueShouldBe+=partyDueShouldBe;" +
                "pre.sumOfPartyDues += doc.map.partyDues;" +
                "}";
        Query query = new Query();
        query.addCriteria(new Criteria("map.year").is(year))
                .addCriteria(new Criteria("map.month").is(month))
                .addCriteria(new Criteria("map.dataType").is("task"));
        DBObject result = mongoTemplate.group("BrowseRecord",new BasicDBObject("map.roleName", 1).append("map.roleId", 0),
                query.getQueryObject(), new BasicDBObject("count", 0)
                                .append("sumOfPartyDueShouldBe", 0)
                                .append("sumOfPartyDues", 0),  reduce);
        if (null == result) {
            return null;
        }

    }*/

}
