package com.jlkj.web.shopnew.enums;

public enum EnumIsShare {

    SHARE_URL_CLICKED(1,"分享链接被点击"),

            ;

    private int code;

    private String name;

    EnumIsShare(int code, String name) {
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
