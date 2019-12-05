package com.jlkj.web.shopnew.dao;

import com.jlkj.web.shopnew.dto.OrdersDto;
import com.jlkj.web.shopnew.dto.OrdersDto;
import com.jlkj.web.shopnew.pojo.Orders;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import com.jlkj.web.shopnew.request.GetLogListByLogTypeAndStaffId;
import com.jlkj.web.shopnew.request.GetOrderListByUserTypeRequest;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersMapper extends BaseDao<Orders, Integer> {

    List<OrdersDto> getOrderListByStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定员工相关的订单记录

    int getOrderCountByStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定员工相关的订单数

    int getOrderCountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定普通员工或老板员工相关的订单数

    BigDecimal getOrderInviteAmountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取累计所有员工邀请订单总额 新

    BigDecimal getOrderAmountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取累计所有员工订单总额 新

    int getOrderCountByBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定老板商城订单数

    int getOrderCountByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest);//按服务商获取订单数

    List<OrdersDto> getOrderListByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest);//按服务商获取订单详情

    BigDecimal getOrderAmountByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest);//获取服务商订单总额

    List<OrdersDto> getOrderListByBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//boss 商城订单详情

}