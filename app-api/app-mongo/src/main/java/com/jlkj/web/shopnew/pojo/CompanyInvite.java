package com.jlkj.web.shopnew.pojo;

import com.jlkj.web.shopnew.pojo.entity.CompanyInviteEntity;

public class CompanyInvite extends CompanyInviteEntity {
    private static final long serialVersionUID = 1L;

    private String companyName;

    private String bossName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }
}