package com.jlkj.web.shopnew.request;


import lombok.Data;

@Data
public class SaveMyCompanyRequest {

    private String companyName;

    private int bossId;

    private String companyAddress;

    private String companyIcon;
}
