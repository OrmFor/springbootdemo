package com.jlkj.web.shopnew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaticsDto {
    private int potentialUserCountResult;
    private int potentialUserAddCountResult;

    private int intentionalUserCountResult;
    private int intentionalUserAddCountResult;

    private int customerUserCountResult;
    private int customerUserAddCountResult;

    private int chatCountResult;
    private int chatAddCountResult;

    private int potentialUserFollowCountResult;
    private int potentialUserFollowAddCountResult;

    private int intentionalUserFollowCountResult;
    private int intentionalUserFollowAddCountResult;

    private int followAfterChatCountResult;
    private int followAfterChatAddCountResult;

    //订单数
    private int orderCountResult;
    private int orderAddCountResult;

    //营业额
    private BigDecimal amountCountResult;
    private BigDecimal amountAddCountResult;


}
