package com.jlkj.web.shopnew.enums;

public enum EnumUserType {

    POTENTIAL_USER(0,"潜在客户"),
    INTENTION_USER(1,"意向客户"),
    REAL_USER(2,"客户"),

    ;

    private int code;

    private String name;

    EnumUserType(int code, String name) {
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
