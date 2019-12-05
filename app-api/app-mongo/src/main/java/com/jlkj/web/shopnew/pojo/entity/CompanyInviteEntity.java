package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class CompanyInviteEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer bossId;
	private Integer uid;
	private Integer companyId;
	private Integer flag;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private Integer role;
	
    public Integer getId() {
        return id;
    }
	public CompanyInviteEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getBossId() {
        return bossId;
    }
	public CompanyInviteEntity setBossId(Integer bossId) {
        this.bossId = bossId;
        return this;
    }
    public Integer getUid() {
        return uid;
    }
	public CompanyInviteEntity setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
    public Integer getCompanyId() {
        return companyId;
    }
	public CompanyInviteEntity setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }
    public Integer getFlag() {
        return flag;
    }
	public CompanyInviteEntity setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public CompanyInviteEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public CompanyInviteEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getRole() {
        return role;
    }
	public CompanyInviteEntity setRole(Integer role) {
        this.role = role;
        return this;
    }
}