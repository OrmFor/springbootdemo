package com.jlkj.web.shopnew.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersDto {

    private String username;//微信昵称

    private int payTime;//订单状态更新时间

    private BigDecimal realPrice;//实付金额

    private String logo;//微信头像

    private String goodName;//商品名称
}
