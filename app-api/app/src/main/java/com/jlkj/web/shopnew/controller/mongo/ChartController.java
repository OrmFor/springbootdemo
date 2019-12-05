package com.jlkj.web.shopnew.controller.mongo;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.StaticsChartCountDto;
import com.jlkj.web.shopnew.request.GetChartDataRequest;
import com.jlkj.web.shopnew.request.GetLogListByLogTypeAndUidRequest;
import com.jlkj.web.shopnew.service.IChart;
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

public class ChartController extends BaseController {

    @Autowired
    private IChart chartService;

    @Autowired
    private ILogMongo logMongoService;

    //商城雷达 统计折线图
    @RequestMapping(value = "/getChartData", method = RequestMethod.POST)
    public ResultCode getChartData(@RequestBody GetChartDataRequest getChartDataRequest){
        List<StaticsChartCountDto> data =  this.chartService.getChartData(getChartDataRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }


    //获取指定logType下的所有用户对自己商城的足迹
    @RequestMapping(value = "/getLogListByLogTypeAndUid",method = RequestMethod.POST)
    public ResultCode getListByLogType(@RequestBody GetLogListByLogTypeAndUidRequest getLogListByLogTypeAndUidRequest){
        Map<String,Object> data = this.logMongoService.getLogListByLogTypeAndUid(getLogListByLogTypeAndUidRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }


}
