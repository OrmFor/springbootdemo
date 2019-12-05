package com.jlkj.web.shopnew.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class StaffAnalysisDto {

    //1 客户总数：员工的客户总数/企业总客户数*100
    //2 跟进客户：员工的跟进客户数/企业总跟进客户数*100
    //3 昨日新增客户：员工的昨日新增客户数/企业昨日的跟进客户数*100
    //4 客户咨询数：员工昨日的咨询数/企业昨日总的咨询数*100
    //5 客户转化率：员工的客户转化率*100
    //6 成交订单：员工的成交订单/企业的成交订单*100

    private BigDecimal customerScale;
    private BigDecimal followScale;
    private BigDecimal customerAddScale;
    private BigDecimal chatScale;
    private BigDecimal rateScale;
    private BigDecimal ordersScale;
    private BigDecimal allScale;


    private int staffId;
    private String staffPic;
    private String staffName;
    private String staffPosition;
}
