package com.jlkj.web.shopnew.enums;

public enum EnumYqsOrderStatus {

    YQS_ORDER_CANCEL(0,"订单取消"),
    YQS_ORDER_WAIT_PAY(10,"已提交待付款"),
    YQS_ORDER_DISCARD(15,"线下付款提交申请(已经取消该付款方式)"),
    YQS_ORDER_CASH_ON_DELIVERY(16,"货到付款"),
    YQS_ORDER_WAIT_DELIEVE(20,"已付款待发货"),
    YQS_ORDER_DELIEVED(30,"已发货待收货"),
    YQS_ORDER_SUCCESS(50,"已完成");

    private int status;

    private String name;

    EnumYqsOrderStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
