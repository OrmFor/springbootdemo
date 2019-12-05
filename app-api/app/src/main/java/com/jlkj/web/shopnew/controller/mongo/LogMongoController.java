package com.jlkj.web.shopnew.controller.mongo;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.OrdersDto;
import com.jlkj.web.shopnew.request.*;
import com.jlkj.web.shopnew.service.IOrders;
import com.jlkj.web.shopnew.service.mongo.ILogMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class LogMongoController extends BaseController {


    @Autowired
    private ILogMongo logMongoService;
    @Autowired
    private IOrders ordersService;

    //（客户雷达）
    @RequestMapping(value = "/getMyCustomerLog" , method = RequestMethod.POST)
    public ResultCode getMyCustomerLog(@RequestBody GetMyCustomerLogResquest getMyCustomerLogResquest){
//        Map<String,Object> data = this.logMongoService.getMyCustomerLog(getMyCustomerLogResquest);
        Map<String,Object> data = this.logMongoService.getMyCustomerLogNew(getMyCustomerLogResquest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }


    //保存足迹
    @RequestMapping(value = "/saveLog",method = RequestMethod.POST)
    public ResultCode saveLog(@RequestBody SaveLogRequest saveLogRequest){
        this.logMongoService.saveLogMongo(saveLogRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //获取指定logType下的所有用户足迹
    @RequestMapping(value = "/getLogListByLogType",method = RequestMethod.POST)
    public ResultCode getListByLogType(@RequestBody GetListByLogTypeRequest getListByLogTypeRequest){
//        Map<String,Object> data = this.logMongoService.getListByLogType(getListByLogTypeRequest);
        Map<String,Object> data = this.logMongoService.getListByLogTypeNew(getListByLogTypeRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //获取指定用户下的所有足迹
    @RequestMapping(value = "/getLogListByUid",method = RequestMethod.POST)
    public ResultCode getLogListByUid(@RequestBody GetListByUidRequest getListByUidRequest){
//        Map<String,Object> data = this.logMongoService.getLogListByUid(getListByUidRequest);
        Map<String,Object> data = this.logMongoService.getLogListByUidNew(getListByUidRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }



    //客户详情 获取指定客户的所有行为足迹
    @RequestMapping(value = "/getLogListByCustomer",method = RequestMethod.POST)
    public ResultCode getLogListByUid(@RequestBody GetLogListByCustomerRequest getLogListByCustomerRequest){
//        Map<String,Object> data = this.logMongoService.getLogListByCustomer(getLogListByCustomerRequest);
        Map<String,Object> data = this.logMongoService.getLogListByCustomerNew(getLogListByCustomerRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }


    @RequestMapping(value = "/getLogCountByLogTypeAndUid",method = RequestMethod.POST)
    @Deprecated
    public ResultCode getListByLogType(@RequestBody GetLogCountByLogTypeAndUidRequest getLogCountByLogTypeAndUidRequest){
        Map<String,Object> data = this.logMongoService.getLogCountByLogTypeAndUid(getLogCountByLogTypeAndUidRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    // 统计不同行为的记录数(人脉雷达/商城雷达)
    @RequestMapping(value = "/getLogListCountByLogType",method = RequestMethod.POST)
    public ResultCode getLogListCountByLogType(@RequestBody GetLogCountByLogTypeAndUidRequest getLogCountByLogTypeAndUidRequest){
        Map<String,Object> data = this.logMongoService.getLogListCountByLogType(getLogCountByLogTypeAndUidRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //获取指定员工相关的所有跟进记录
    @RequestMapping(value = "/getFollowListByLogType",method = RequestMethod.POST)
    public ResultCode getFollowListByLogType(@RequestBody GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId){
        Map<String, Object> data = logMongoService.getFollowListByLogType(getLogListByLogTypeAndStaffId);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //获取指定员工相关的分享记录
    @RequestMapping(value = "/getShareListByLogTypeAndStaffId",method = RequestMethod.POST)
    public ResultCode getShareListByLogTypeAndStaffId(@RequestBody GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId){
        Map<String, Object> data = logMongoService.getShareListByLogTypeAndStaffId(getLogListByLogTypeAndStaffId);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //获取指定员工相关的订单记录
    @RequestMapping(value = "/getOrderListByStaffId",method = RequestMethod.POST)
    public ResultCode getOrderListByStaffId(@RequestBody GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId){
       Map<String,Object> data = ordersService.getOrderListByStaffId(getLogListByLogTypeAndStaffId);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //获取指定员工相关的聊天记录
    @RequestMapping(value = "/getChartListByLogTypeAndStaffId",method = RequestMethod.POST)
    public ResultCode getChartListByLogTypeAndStaffId(@RequestBody GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId){
        Map<String, Object> data = logMongoService.getChartListByLogTypeAndStaffId(getLogListByLogTypeAndStaffId);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

}
