package com.jlkj.web.shopnew.request;


import lombok.Data;

@Data
public class ModifyCustomerRequest {

    private int uid;

    private int belongUid;

    private int userType;
}
