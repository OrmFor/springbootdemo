package com.jlkj.web.shopnew.enums;

public enum EnumTimeType {


    POTENTIALTIME("potentialTime"),
    INTENTIONALTIME("intentionalTime"),
    CUSTOMERTIME("customerTime");

    private String timeType;

    EnumTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTimeType() {
        return timeType;
    }


}
