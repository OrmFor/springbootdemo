package com.jlkj.web.shopnew.enums;

public enum EnumAccountType {

    KINLIE(0,"尽猎"),

    COMPANY(1,"企业"),
    GROUP(2,"集团"),

            ;

    private int type;

    private String name;

    EnumAccountType(int type, String name) {
        this.type = type;
        this.name = name;
    }


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
