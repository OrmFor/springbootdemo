package com.jlkj.web.shopnew.enums;

public enum EnumStaffType {

    BOSS(0,"BOSS"),

    STAFF(1,"员工"),
    PARTNER(2,"合伙人"),

    ;

    private int code;

    private String name;

    EnumStaffType(int code, String name) {
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
