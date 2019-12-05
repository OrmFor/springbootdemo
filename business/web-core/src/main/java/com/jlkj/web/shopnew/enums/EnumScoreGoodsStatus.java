package com.jlkj.web.shopnew.enums;

public enum EnumScoreGoodsStatus {
    GOODS_ON(0,"上架"),

    GOODS_OFF(1, "下架");

    private int status;

    private String name;

    EnumScoreGoodsStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
