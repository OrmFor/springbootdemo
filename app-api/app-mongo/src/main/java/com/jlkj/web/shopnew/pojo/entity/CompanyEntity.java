package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CompanyEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer bossId;
	private String companyName;
	private String companyAddress;
	private String companyIcon;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private Integer status;
	private Integer initNum;
	
    public Integer getId() {
        return id;
    }
	public CompanyEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getBossId() {
        return bossId;
    }
	public CompanyEntity setBossId(Integer bossId) {
        this.bossId = bossId;
        return this;
    }
    public String getCompanyName() {
        return companyName;
    }
	public CompanyEntity setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }
    public String getCompanyAddress() {
        return companyAddress;
    }
	public CompanyEntity setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }
    public String getCompanyIcon() {
        return companyIcon;
    }
	public CompanyEntity setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CompanyEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public CompanyEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public CompanyEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getInitNum() {
        return initNum;
    }
	public CompanyEntity setInitNum(Integer initNum) {
        this.initNum = initNum;
        return this;
    }
}