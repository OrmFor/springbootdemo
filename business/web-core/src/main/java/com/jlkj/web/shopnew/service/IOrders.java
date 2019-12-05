package com.jlkj.web.shopnew.service;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.pojo.Orders;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IOrders extends BaseService<Orders, Integer> {
    void modifyOrders(JSONObject json);

    Orders selectByYqsOrdreNum(String orderNo);
}