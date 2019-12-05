package com.jlkj.web.shopnew.service;

import cc.s2m.util.Page;
import com.jlkj.web.shopnew.dto.ScoreGoodsDto;
import com.jlkj.web.shopnew.pojo.ScoreOrder;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.jlkj.web.shopnew.request.GetUserOrderListRequest;
import com.jlkj.web.shopnew.yidto.request.OrderRequest;
import com.jlkj.web.shopnew.yidto.request.ProcessOrderNumRequest;
import com.jlkj.web.shopnew.yidto.request.ProcessOrderRequest;

import java.util.List;

public interface IScoreOrder extends BaseService<ScoreOrder, Integer> {
    Page<ScoreOrder> getScoreOrders(GetUserOrderListRequest getUserOrderListRequest);

    void createOrder(OrderRequest orderRequest);

    void processOrder(ProcessOrderRequest processOrderRequest);

    void processOrderNum(ProcessOrderNumRequest processOrderNumRequest);
}