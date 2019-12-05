package com.jlkj.web.shopnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.constant.ConstantDateType;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dao.OrdersMapper;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.dto.OrdersDto;
import com.jlkj.web.shopnew.enums.EnumOrderStatus;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.pojo.Orders;
import com.jlkj.web.shopnew.pojo.Staff;
import com.jlkj.web.shopnew.request.GetLogListByLogTypeAndStaffId;
import com.jlkj.web.shopnew.request.GetOrderListByUserTypeRequest;
import com.jlkj.web.shopnew.request.GetUserInfoRequest;
import com.jlkj.web.shopnew.request.StatiscRequest;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IManagers;
import com.jlkj.web.shopnew.service.IOrders;
import com.jlkj.web.shopnew.service.IStaff;
import com.jlkj.web.shopnew.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersImpl extends BaseServiceImpl<Orders, OrdersMapper, Integer> implements IOrders {

    private static final Logger LOGGER = LogManager.getLogger(LogMongoImpl.class);

    @Autowired
    private OrdersMapper ordersMapper;

    protected OrdersMapper getDao() {
        return ordersMapper;
    }

    @Autowired
    private IStaff staffService;

    @Autowired
    private IManagers managersService;

    @Autowired
    private ICompany companyService;

    private static final List LIST_ORDER_STATUS = Lists.newArrayList(
            EnumOrderStatus.SUCCESS.getCode(),
            EnumOrderStatus.DELETE.getCode()
    );

    @Override
    public Integer getOrdersCount(StatiscRequest statiscRequest) {

        int mgId = managersService.getMgIdByUid(statiscRequest.getUid());

        DateDto dateDto = DateUtil.getDayByType(statiscRequest.getDateType(), statiscRequest.getStartDay(),
                statiscRequest.getEndDay());
        Orders condi = new Orders();

        /*if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            condi.and(Expressions.in("uid", list));

        } else {*/
        condi.setMgId(String.valueOf(mgId));
        //}

        condi.and(Expressions.in("status", LIST_ORDER_STATUS));
        condi.and(Expressions.le("update_time",
                DateUtil.getLastSecIntegralTime(dateDto.getEndDay())));
        condi.and(Expressions.ge("update_time",
                DateUtil.getFirstSecIntegralTime(dateDto.getStartDay())));

        int count = this.getCount(condi);

        return count;
    }

    @Override
    public Integer getOrdersCount(StatiscRequest statiscRequest, DateDto dateDto) {
        int mgId = managersService.getMgIdByUid(statiscRequest.getUid());//根据用户id(boss)获取对应的商户id

        Orders condi = new Orders();

        /*if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            condi.and(Expressions.in("uid", list));

        } else {*/
        condi.setMgId(String.valueOf(mgId));//某个商户所有相关的订单
        //}

        condi.and(Expressions.in("status", LIST_ORDER_STATUS));//付款成功的订单

        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {//累计
            condi.and(Expressions.le("update_time",
                    DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndDay()))));
        } else {
            if (dateDto.getEndDay() != null) {
                condi.and(Expressions.le("update_time",
                        DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndDay()))));
            }

            if (dateDto.getStartDay() != null) {
                condi.and(Expressions.ge("update_time",
                        DateUtil.getTime(DateUtil.getFirstSecIntegralTime(dateDto.getStartDay()))));
            }

        }


        int count = this.getCount(condi);//boss用户(商户)的所有成功交易订单数

        return count;
    }

    @Override
    public Integer getOrdersCount(int uid, DateDto dateDto) {

        int mgId = managersService.getMgIdByUid(uid);

        Orders condi = new Orders();
        condi.setMgId(String.valueOf(mgId));
        condi.and(Expressions.in("status", LIST_ORDER_STATUS));
        condi.and(Expressions.le("update_time",
                DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndDay()))));
        condi.and(Expressions.ge("update_time",
                DateUtil.getTime(DateUtil.getFirstSecIntegralTime(dateDto.getStartDay()))));
        int count = this.getCount(condi);
        return count;
    }

    @Override
    public BigDecimal getRealPriceCount(StatiscRequest statiscRequest, DateDto dateDto) {
        int mgId = managersService.getMgIdByUid(statiscRequest.getUid());

        Orders condi = new Orders();

        /*if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            condi.and(Expressions.in("uid", list));

        } else {*/
        condi.setMgId(String.valueOf(mgId));
        //}

        condi.and(Expressions.in("status", LIST_ORDER_STATUS));
        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {
            condi.and(Expressions.le("update_time",
                    DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndAddDay()))));
        } else {
            if (dateDto.getEndDay() != null) {
                condi.and(Expressions.le("update_time",
                        DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndAddDay()))));
            }

            if (dateDto.getStartDay() != null) {
                condi.and(Expressions.ge("update_time",
                        DateUtil.getTime(DateUtil.getFirstSecIntegralTime(dateDto.getStartAddDay()))));
            }

        }


        Map<String, ?> maxMap = this.aggregate(condi, new String[]{"sum"}, new String[]{"real_price"});

        BigDecimal sumPrice = BigDecimal.ZERO;

        if (maxMap != null) {
            sumPrice = (BigDecimal) maxMap.get("sum_real_price");
        }

        return sumPrice;
    }

    @Override
    public Integer getOrdersAddCount(StatiscRequest statiscRequest, DateDto dateDto) {
        int mgId = managersService.getMgIdByUid(statiscRequest.getUid());

        Orders condi = new Orders();

        /*if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            condi.and(Expressions.in("uid", list));

        } else {*/
        condi.setMgId(String.valueOf(mgId));
        //}

        condi.and(Expressions.in("status", LIST_ORDER_STATUS));

        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {
            condi.and(Expressions.le("update_time",
                    DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndAddDay()))));
        } else {
            if (dateDto.getEndDay() != null) {
                condi.and(Expressions.le("update_time",
                        DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndAddDay()))));
            }

            if (dateDto.getStartDay() != null) {
                condi.and(Expressions.ge("update_time",
                        DateUtil.getTime(DateUtil.getFirstSecIntegralTime(dateDto.getStartAddDay()))));
            }

        }


        int count = this.getCount(condi);

        return count;
    }

    @Override
    public BigDecimal getRealPriceAddCount(StatiscRequest statiscRequest, DateDto dateDto) {
        int mgId = managersService.getMgIdByUid(statiscRequest.getUid());

        Orders condi = new Orders();

        /*if (statiscRequest.isBoss()) {
            List<Integer> list = staffService.getCompanyStaff(statiscRequest.getUid());
            condi.and(Expressions.in("uid", list));

        } else {*/
        condi.setMgId(String.valueOf(mgId));
        //}

        if (ConstantDateType.ALL_DAY.equals(statiscRequest.getDateType())) {
            condi.and(Expressions.le("update_time",
                    DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndAddDay()))));
        } else {
            if (dateDto.getEndDay() != null) {
                condi.and(Expressions.le("update_time",
                        DateUtil.getTime(DateUtil.getLastSecIntegralTime(dateDto.getEndAddDay()))));
            }

            if (dateDto.getStartDay() != null) {
                condi.and(Expressions.ge("update_time",
                        DateUtil.getTime(DateUtil.getFirstSecIntegralTime(dateDto.getStartAddDay()))));
            }

        }
        condi.and(Expressions.in("status", LIST_ORDER_STATUS));


        Map<String, ?> maxMap = this.aggregate(condi, new String[]{"sum"}, new String[]{"real_price"});

        BigDecimal sumPrice = BigDecimal.ZERO;

        if (maxMap != null) {
            sumPrice = (BigDecimal) maxMap.get("sum_real_price");
        }

        return sumPrice;
    }

    @Override
    public int getOrders(GetUserInfoRequest getUserInfoRequest) {
        int mgId = managersService.getMgIdByUid(getUserInfoRequest.getOperationId());

        Orders condi = new Orders();
        condi.setMgId(String.valueOf(mgId));
        condi.setUid(getUserInfoRequest.getUid());
        condi.and(Expressions.in("status", LIST_ORDER_STATUS));

        int count = this.getCount(condi);
        return count;
    }

    //获取指定员工邀请相关的订单记录
    @Override
    public Map<String, Object> getOrderListByStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        DateDto dateDto = DateUtil.getDayByType(getLogListByLogTypeAndStaffId.getDateType(),
                getLogListByLogTypeAndStaffId.getStartDay(), getLogListByLogTypeAndStaffId.getEndDay());
        getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
        getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());

        Staff conStaff = new Staff();
        conStaff.setStaffId(getLogListByLogTypeAndStaffId.getStaffId());
        Staff staff = staffService.getByCondition(conStaff);

        Company conCompany = new Company();
        conCompany.setId(staff.getCompanyId());

        Company bean = companyService.getByCondition(conCompany);
        if (bean == null) {
            throw new BussinessException(StatusCode.ERROR_NO_COMPANY);
        }

        int mgId = managersService.getMgIdByUid(bean.getBossId());
        getLogListByLogTypeAndStaffId.setMgId(mgId);

        List<OrdersDto> list = ordersMapper.getOrderListByStaffId(getLogListByLogTypeAndStaffId);//获取指定员工相关的订单记录
        int totalCount = getOrderCountByStaffId(getLogListByLogTypeAndStaffId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalCount", totalCount);

        LOGGER.info("[== 获取指定员工邀请相关的订单记录 ==]" + list + "[容量]" + list.size());
        return map;
    }

    //获取指定员工邀请相关的订单数
    public int getOrderCountByStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        int count = ordersMapper.getOrderCountByStaffId(getLogListByLogTypeAndStaffId);
        LOGGER.info("[==获取指定员工邀请相关的订单数==]" + count);
        return count;
    }

    //获取指定普通员工邀请相关订单数(选择日期类型处理后设置id列表)
    public int getOrderCountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        int count = ordersMapper.getOrderCountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);
        LOGGER.info("[==获取指定普通员工或老板员工邀请相关的订单数 ^_^==]" + count);
        return count;
    }

    //获取指定老板商城订单数(选择日期类型处理后设置id列表)
    public int getOrderCountByBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        int count = ordersMapper.getOrderCountByBossId(getLogListByLogTypeAndStaffId);
        LOGGER.info("[==获取指定老板商城订单数 ^_^==]" + count);
        return count;
    }

    //获取累计所有员工/员工 邀请订单总额 新
    public BigDecimal getOrderInviteAmountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        BigDecimal sum = ordersMapper.getOrderInviteAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);
        BigDecimal amount = sum == null ? new BigDecimal(0) : sum;
        LOGGER.info("[==获取累计所有员工邀请订单总额 新 ^_^==]" + amount);
        return amount;
    }

    //获取累计所有员工订单总额 新
    public BigDecimal getOrderAmountByStaffIdOrBossId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId) {
        BigDecimal sum = ordersMapper.getOrderAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);
        BigDecimal amount = sum == null ? new BigDecimal(0) : sum;
        LOGGER.info("[==获取累计所有员工订单总额 新 ^_^==]" + amount);
        return amount;
    }

    //获取累计所有员工/员工 订单总数 新
    @Override
    public Integer getOrdersCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId=new GetLogListByLogTypeAndStaffId();
        int count=0;

        if(!ConstantDateType.SELECT_DAY.equals(statiscRequest.getDateType())){//累计到昨天
            dateDto.setStartDay(null);
        }

        if(statiscRequest.isBoss()){
            getLogListByLogTypeAndStaffId.setStaffId(statiscRequest.getUid());//老板id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            count=getOrderCountByBossId(getLogListByLogTypeAndStaffId);//boss 商城订单数
        }else{
            getLogListByLogTypeAndStaffId.setStaffIds(Lists.newArrayList(statiscRequest.getUid()));//员工id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            getLogListByLogTypeAndStaffId.setMgId(statiscRequest.getMgId());
            count=getOrderCountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//staff 邀请订单数
        }

        return count;
    }

    //获得所有员工/员工 新增订单总数 新
    public Integer getOrdersAddCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId=new GetLogListByLogTypeAndStaffId();
        int count=0;

        if(statiscRequest.isBoss()){
            getLogListByLogTypeAndStaffId.setStaffId(statiscRequest.getUid());//老板id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartAddDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndAddDay());
            count=getOrderCountByBossId(getLogListByLogTypeAndStaffId);//boss 新增商城订单数
        }else{
            getLogListByLogTypeAndStaffId.setStaffIds(Lists.newArrayList(statiscRequest.getUid()));//员工id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartAddDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndAddDay());
            getLogListByLogTypeAndStaffId.setMgId(statiscRequest.getMgId());
            count=getOrderCountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//staff 新增邀请订单数
        }

        return count;
    }

    @Override
    public Integer getOrdersStaffCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId = new GetLogListByLogTypeAndStaffId();
        int count = 0;

        if (statiscRequest.isBoss()) {
            getLogListByLogTypeAndStaffId.setStaffId(statiscRequest.getUid());//老板id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            getLogListByLogTypeAndStaffId.setStatuss(LIST_ORDER_STATUS);
            count = getOrderCountByBossId(getLogListByLogTypeAndStaffId);//boss 商城订单数
        } else {
            getLogListByLogTypeAndStaffId.setStaffIds(Lists.newArrayList(statiscRequest.getUid()));//员工id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            getLogListByLogTypeAndStaffId.setMgId(statiscRequest.getMgId());
            count = getOrderCountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//staff 邀请订单数
        }

        return count;
    }

    //统计boss/staff (累计)商业邀请总额
    public BigDecimal getRealPriceInviteCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId = new GetLogListByLogTypeAndStaffId();
        BigDecimal sumPrice = new BigDecimal(0);

        if (!ConstantDateType.SELECT_DAY.equals(statiscRequest.getDateType())) {//累计到昨天
            dateDto.setStartDay(null);
        }

        if (statiscRequest.isBoss()) {
            List<Integer> staffUids = staffService.getStaffUids(statiscRequest.getUid());//老板id
            getLogListByLogTypeAndStaffId.setStaffIds(staffUids);
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            sumPrice = getOrderInviteAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//boss 累计商业邀请总额
        } else {
            getLogListByLogTypeAndStaffId.setStaffIds(Lists.newArrayList(statiscRequest.getUid()));//员工id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            getLogListByLogTypeAndStaffId.setMgId(statiscRequest.getMgId());
            sumPrice = getOrderInviteAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//staff 累计商业邀请总额
        }

        return sumPrice;
    }

    //统计boss/staff 新增商业邀请总额
    public BigDecimal getRealPriceAddInviteCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId = new GetLogListByLogTypeAndStaffId();
        BigDecimal sumPrice = new BigDecimal(0);

        if (statiscRequest.isBoss()) {
            List<Integer> staffUids = staffService.getStaffUids(statiscRequest.getUid());
            getLogListByLogTypeAndStaffId.setStaffIds(staffUids);
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            sumPrice = getOrderInviteAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//boss 商业新增邀请总额
        } else {
            getLogListByLogTypeAndStaffId.setStaffIds(Lists.newArrayList(statiscRequest.getUid()));//员工id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            getLogListByLogTypeAndStaffId.setMgId(statiscRequest.getMgId());
            sumPrice = getOrderInviteAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//staff 商业新增邀请总额
        }

        return sumPrice;
    }

    //统计boss/staff (累计)商业总额 新
    public BigDecimal getRealPriceCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId=new GetLogListByLogTypeAndStaffId();
        BigDecimal sumPrice=new BigDecimal(0);

        if(!ConstantDateType.SELECT_DAY.equals(statiscRequest.getDateType())){//累计到昨天
            dateDto.setStartDay(null);
        }

        if(statiscRequest.isBoss()){
            getLogListByLogTypeAndStaffId.setStaffId(statiscRequest.getUid());//boss id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            sumPrice=getOrderAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//boss 累计商业总额
        }else{
            sumPrice=getRealPriceInviteCountNew(statiscRequest,dateDto);//staff 累计商业(邀请)总额
        }

        return sumPrice;
    }

    //统计boss/staff 新增商业总额 新
    public BigDecimal getRealPriceAddCountNew(StatiscRequest statiscRequest, DateDto dateDto) {
        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId=new GetLogListByLogTypeAndStaffId();
        BigDecimal sumPrice=new BigDecimal(0);

        if(statiscRequest.isBoss()){
            getLogListByLogTypeAndStaffId.setStaffId(statiscRequest.getUid());//boss id
            getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
            getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
            sumPrice=getOrderAmountByStaffIdOrBossId(getLogListByLogTypeAndStaffId);//boss 新增商业总额
        }else{
            sumPrice=getRealPriceAddInviteCountNew(statiscRequest,dateDto);//staff 新增商业总额
        }

        return sumPrice;
    }

    //按服务商获取订单数
    @Override
    public int getOrderCountByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest) {
        return this.ordersMapper.getOrderCountByServiceProvider(getOrderListByUserTypeRequest);
    }

    //获取服务商订单总额
    @Override
    public BigDecimal getOrderAmountByServiceProvider(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest) {
        BigDecimal sum = ordersMapper.getOrderAmountByServiceProvider(getOrderListByUserTypeRequest);
        BigDecimal amount = sum == null ? new BigDecimal(0) : sum;
        LOGGER.info("[==获取服务商订单总额 ==]" + amount);
        return amount;
    }

    //分类获取订单详情
//    @Override
//    public List<GetOrderListByUserTypeDto> getOrderListByUserType(GetOrderListByUserTypeRequest getOrderListByUserTypeRequest) {
//        DateDto dateDto = DateUtil.getDayByType(getOrderListByUserTypeRequest.getDateType(),
//                getOrderListByUserTypeRequest.getStartDay(), getOrderListByUserTypeRequest.getEndDay());
//        Staff staff = staffService.getStaffByStaffId(getOrderListByUserTypeRequest.getUid());
//        int mgId=managersService.getMgIdByStaffId(getOrderListByUserTypeRequest.getUid());
//
//        GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId=new GetLogListByLogTypeAndStaffId();
//        getLogListByLogTypeAndStaffId.setStaffId(getOrderListByUserTypeRequest.getUid());
//        getLogListByLogTypeAndStaffId.setStartDay(dateDto.getStartDay());
//        getLogListByLogTypeAndStaffId.setEndDay(dateDto.getEndDay());
//        getOrderListByUserTypeRequest.setMgId(mgId);
//
//        if (getOrderListByUserTypeRequest.isBoss() || (staff.getRole() == EnumStaffType.PARTNER_ALL.getCode())) {
//            return ordersMapper.getOrderListByBossId(getLogListByLogTypeAndStaffId);//boss 商城订单详情
//        }
//
//        if (!getOrderListByUserTypeRequest.isBoss() && staff.getServicepLevel() > 0) {
//            getOrderListByUserTypeRequest.setStartDay(dateDto.getStartDay());
//            getOrderListByUserTypeRequest.setEndDay(dateDto.getEndDay());
//            return ordersMapper.getOrderListByServiceProvider(getOrderListByUserTypeRequest);//服务商订单详情
//        }
//
//        return ordersMapper.getOrderListByStaffId(getLogListByLogTypeAndStaffId);//指定员工订单详情
//    }

}