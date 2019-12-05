package com.jlkj.web.shopnew.service.impl;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dao.ScoreOrderMapper;
import com.jlkj.web.shopnew.dto.ScoreGoodsDto;
import com.jlkj.web.shopnew.enums.EnumOrderStatus;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.ScoreOrder;
import com.jlkj.web.shopnew.request.GetUserOrderListRequest;
import com.jlkj.web.shopnew.service.IScoreOrder;
import com.jlkj.web.shopnew.yidto.request.OrderRequest;
import com.jlkj.web.shopnew.yidto.request.ProcessOrderNumRequest;
import com.jlkj.web.shopnew.yidto.request.ProcessOrderRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ScoreOrderImpl extends BaseServiceImpl<ScoreOrder, ScoreOrderMapper, Integer> implements IScoreOrder {
    @Autowired
    private ScoreOrderMapper scoreOrderMapper;

    protected ScoreOrderMapper getDao() {
        return scoreOrderMapper;
    }

    @Override
    public Page<ScoreOrder> getScoreOrders(GetUserOrderListRequest getUserOrderListRequest) {
        ScoreOrder condi = new ScoreOrder();
        condi.setOrderBy("create_time desc");
        condi.and(Expressions.notIn("order_status",Lists.newArrayList(EnumOrderStatus.DELETE.getCode())));
        condi.setUid(getUserOrderListRequest.getUid());
        condi.setPageNumber(getUserOrderListRequest.getPage());
        condi.setPageSize(getUserOrderListRequest.getPageSize());
        Page<ScoreOrder> page = getPage(condi,null);
        return page;
    }

    @Override
    public void createOrder(OrderRequest orderRequest) {
        ScoreOrder bean = new ScoreOrder();
        bean.setOrderNum(orderRequest.getOrderNum());
        bean.setUid(orderRequest.getUid());
        bean.setMgId(orderRequest.getMgId());
        bean.setOrderPrice(orderRequest.getOrderPrice());
        bean.setOrderIntegralPrice(orderRequest.getOrderIntegralPrice());
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        bean.setOrderStatus(EnumOrderStatus.WAIT_PAY.getCode());
        bean.setResult(orderRequest.getResult());
        this.insert(bean);
    }

    @Override
    public void processOrder(ProcessOrderRequest processOrderRequest) {
        if(StringUtils.isBlank(processOrderRequest.getOrderNum())
                && processOrderRequest.getId() == null){

            throw new BussinessException(StatusCode.ERROR_ORDER_NUM);
        }

        ScoreOrder condi = new ScoreOrder();
        if(processOrderRequest.getOrderNum() != null) {
            condi.setOrderNum(processOrderRequest.getOrderNum());

        }
        if(processOrderRequest.getId() != null){
            condi.setId(processOrderRequest.getId());
        }

        ScoreOrder bean = new ScoreOrder();
        bean.setOrderStatus(processOrderRequest.getOrderStatus());
        if(processOrderRequest.getOrderStatus() == EnumOrderStatus.SUCCESS.getCode()) {
            bean.setReceiveTime(new Date());
        }

        bean.setUpdateTime(new Date());


        this.updateByCondition(bean,condi);
    }

    @Override
    public void processOrderNum(ProcessOrderNumRequest processOrderNumRequest) {
        ScoreOrder condi = new ScoreOrder();
        condi.setSelfOrderNum(processOrderNumRequest.getSelfOrderNum());
        ScoreOrder update = new ScoreOrder();
        update.setOrderNum(processOrderNumRequest.getOrderNum());
        this.updateByCondition(update,condi);
    }
}