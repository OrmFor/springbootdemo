package com.jlkj.web.shopnew.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.dao.OrdersMapper;
import com.jlkj.web.shopnew.enums.EnumOrderStatus;
import com.jlkj.web.shopnew.pojo.Orders;
import com.jlkj.web.shopnew.service.IOrders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class OrdersImpl extends BaseServiceImpl<Orders, OrdersMapper, Integer> implements IOrders {
    @Autowired
    private OrdersMapper ordersMapper;

    protected OrdersMapper getDao() {
        return ordersMapper;
    }


    private static final Logger LOGGER = LogManager.getLogger(OrdersImpl.class);


    @Override
    public void modifyOrders(JSONObject json) {
        LOGGER.info(json);
        String orderNo = json.getString("buttonId");

        String status = json.getString("status");

        Orders order = this.selectByYqsOrdreNum(orderNo);
        if (order.getStatus() == (EnumOrderStatus.valueOf(status).getCode()) ) {
            LOGGER.info("订单原状态为order.status={},传入状态status={},不处理",order.getStatus(),status);
            return ;
        }
    }

    @Override
    public Orders selectByYqsOrdreNum(String yqsOrderNum) {
        Orders condi = new Orders();
        Orders orders = getByCondition(condi);
        return orders;
    }
}