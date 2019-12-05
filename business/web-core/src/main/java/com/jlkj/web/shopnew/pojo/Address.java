package com.jlkj.web.shopnew.pojo;

import com.jlkj.web.shopnew.pojo.entity.AddressEntity;

public class Address extends AddressEntity {
    private static final long serialVersionUID = 1L;

    private int isDefault;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int aDefault) {
        isDefault = aDefault;
    }
}