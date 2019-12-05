package com.jlkj.web.shopnew.enums;

public enum EnumOrderStatus {
   // 0用户已取消，1待付款，2已付款待发货，3已发货等待收货，4已收货交易成功，5交易关闭，6售后,7删除，8过期
    USER_CANCLE(0,"用户已取消"),
    WAIT_PAY(1,"待付款"),
    WAIT_SEND(2,"已付款待发货"),
    WAIT_RECEIVING(3,"已发货等待收货"),
    SUCCESS(4,"已收货交易成功"),
    CLOSED(5,"交易关闭"),
    AFTER_SALE(6,"售后"),
    DELETE(7,"删除"),//备注 此删除为 付款成功后 客户 删除订单 ， 并不是取消订单
    EXPIRE(8,"过期"),
    YQS_CANCLE(9,"易企顺取消订单"),
    ;


    private int code;

    private String name;

    EnumOrderStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


}
