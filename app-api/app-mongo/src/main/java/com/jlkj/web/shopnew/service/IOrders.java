package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.dto.GetOrderListByUserTypeDto;
import com.jlkj.web.shopnew.pojo.Orders;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.jlkj.web.shopnew.request.GetLogListByLogTypeAndStaffId;
import com.jlkj.web.shopnew.request.GetOrderListByUserTypeRequest;
import com.jlkj.web.shopnew.request.GetUserInfoRequest;
import com.jlkj.web.shopnew.request.StatiscRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IOrders extends BaseService<Orders, Integer> {
    Integer getOrdersCount(StatiscRequest statiscRequest);

    Integer getOrdersCount(StatiscRequest statiscRequest,DateDto dateDto);//获得累计登陆用户(boss)这个商户的成功订单总数

    Integer getOrdersCount(int uid , DateDto dateDto);

    BigDecimal getRealPriceCount(StatiscRequest statiscRequest,DateDto dateDto);

    Integer getOrdersAddCount(StatiscRequest statiscRequest, DateDto dateDto);

    BigDecimal getRealPriceAddCount(StatiscRequest statiscRequest, DateDto dateDto);

    int getOrders(GetUserInfoRequest getUserInfoRequest);//获取客户产生的订单数

    Map<String,Object> getOrderListByStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定员工相关的订单记录

    int getOrderCountByStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定员工相关的订单数

    Integer getOrdersCountNew(StatiscRequest statiscRequest,DateDto dateDto);//获得累计boss/staff 商城订单总数 新

    Integer getOrdersStaffCountNew(StatiscRequest statiscRequest, DateDto dateDto);

    Integer getOrdersAddCountNew(StatiscRequest statiscRequest, DateDto dateDto);//获得所有员工新增订单总数 新

    BigDecimal getRealPriceInviteCountNew(StatiscRequest statiscRequest,DateDto dateDto);//统计boss(累计)商业邀请总额

    BigDecimal getRealPriceAddInviteCountNew(StatiscRequest statiscRequest,DateDto dateDto);//统计boss新增商业邀请总额

    BigDecimal getRealPriceCountNew(StatiscRequest statiscRequest,DateDto dateDto);//统计boss商业/staff邀请总额 新

    BigDecimal getRealPriceAddCountNew(StatiscRequest statiscRequest,DateDto dateDto);//统计boss新增商业总额 新

    int getOrderCountByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest);//按服务商获取订单数

    int getOrderCountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定普通员工邀请相关订单数

    int getOrderCountByBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取指定老板商城订单数

    BigDecimal getOrderInviteAmountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取累计所有员工/员工 邀请订单总额 新

    BigDecimal getOrderAmountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);//获取累计所有员工订单总额 新

    BigDecimal getOrderAmountByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest);//获取服务商订单总额

//    List<GetOrderListByUserTypeDto> getOrderListByUserType(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest);//获取订单详情

}