package com.jlkj.web.shopnew.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetOrderListByUserTypeDto {

     private int orderId;

     private String addressId;//

     private BigDecimal realPrice;//实际付款金额

     private int payTime;//订单状态更新时间

     private String goodName;//商品名称

     private String username;//微信昵称

     private String logo;//微信头像


}
