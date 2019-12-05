package com.jlkj.web.shopnew.controller.mongo;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.request.FollowMongoRequest;
import com.jlkj.web.shopnew.request.GetFollowListRequest;
import com.jlkj.web.shopnew.service.mongo.IFollowMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class FollowMongoController extends BaseController {

    @Autowired
    private IFollowMongo followMongoService;


    //保存跟进数据
    @RequestMapping(value = "/saveFollow" , method = RequestMethod.POST)
    public ResultCode saveFollow(@RequestBody FollowMongoRequest followMongoRequest){
        this.followMongoService.saveFollow(followMongoRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //查询跟进数据 列表
    @RequestMapping(value = "/getFollowList" , method = RequestMethod.POST)
    public ResultCode getFollowList(@RequestBody GetFollowListRequest getFollowListRequest){
        Map<String,Object> data = this.followMongoService.getFollowList(getFollowListRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

}
