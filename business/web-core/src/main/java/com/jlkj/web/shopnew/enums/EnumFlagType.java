package com.jlkj.web.shopnew.enums;

public enum EnumFlagType {
    COMPANY_INVITE_SEND(0,"发送邀请"),
    COMPANY_INVITE_AGREE(1,"同意"),
    COMPANY_INVITE_REFUSE(2,"拒绝"),
    COMPANY_INVITE_DELETE(3,"逻辑删除"),
            ;

    private int code;

    private String name;

    EnumFlagType(int code, String name) {
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
