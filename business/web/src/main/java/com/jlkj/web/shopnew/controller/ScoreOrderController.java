package com.jlkj.web.shopnew.controller;


import cc.s2m.util.Page;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.ScoreOrder;
import com.jlkj.web.shopnew.request.GetUserOrderListRequest;
import com.jlkj.web.shopnew.service.IScoreOrder;
import com.jlkj.web.shopnew.yidto.request.OrderRequest;
import com.jlkj.web.shopnew.yidto.request.ProcessOrderNumRequest;
import com.jlkj.web.shopnew.yidto.request.ProcessOrderRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class ScoreOrderController {

    @Autowired
    private IScoreOrder scoreOrderService;


    @RequestMapping("/getUserOrderList")
    public ResultCode getUserOrderList(@RequestBody GetUserOrderListRequest getUserOrderListRequest){
        Page<ScoreOrder> page = scoreOrderService.getScoreOrders(getUserOrderListRequest);
        return new ResultCode(StatusCode.SUCCESS,page);
    }


   // @RequestMapping("/createOrder")
    public ResultCode order(@RequestBody OrderRequest orderRequest){
        scoreOrderService.createOrder(orderRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }


    @RequestMapping("/processOrder")
    public ResultCode processOrder(@RequestBody ProcessOrderRequest processOrderRequest){
        if(StringUtils.isBlank(processOrderRequest.getOrderNum())
                && processOrderRequest.getId() == null){
            return new ResultCode(StatusCode.ERROR_ORDER_NUM);
        }
        scoreOrderService.processOrder(processOrderRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }


    @RequestMapping("/processOrderNum")
    public ResultCode processOrderNum(@RequestBody ProcessOrderNumRequest processOrderNumRequest){
        if(StringUtils.isBlank(processOrderNumRequest.getOrderNum())){
            return new ResultCode(StatusCode.ERROR_ORDER_NUM);
        }
        scoreOrderService.processOrderNum(processOrderNumRequest);

        return new ResultCode(StatusCode.SUCCESS);
    }



}
