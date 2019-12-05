package com.jlkj.web.shopnew.request;


import lombok.Data;

@Data
public class ModifyCustomerLabel {
    private int uid;

    private int belongUid;

    private String label;

}
