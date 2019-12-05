package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.dto.GetOrderListByUserTypeDto;
import com.jlkj.web.shopnew.request.GetOrderListByUserTypeRequest;
import com.jlkj.web.shopnew.service.IOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class OrdersController extends BaseController {

    @Autowired
    private IOrders ordersService;

    //分类获取订单详情
//    @RequestMapping(value = "/getOrdersListByUserType" , method = RequestMethod.POST)
//    public Map<String,Object> getOrdersListByUserType(@RequestBody GetOrderListByUserTypeRequest getOrderListByUserTypeRequest){
//        Map<String,Object> data=new HashMap<>();
//        List<GetOrderListByUserTypeDto> list = ordersService.getOrderListByUserType(getOrderListByUserTypeRequest);
//        data.put("list",list);
//        data.put("totalCount",list.size());
//        return data;
//    }

}
