package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysTaskHandelEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String moduleName;
	private String hostname;
	private String ipAddress;
	private Boolean isEnabled;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updatedAt;
	
    public Integer getId() {
        return id;
    }
	public SysTaskHandelEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getModuleName() {
        return moduleName;
    }
	public SysTaskHandelEntity setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }
    public String getHostname() {
        return hostname;
    }
	public SysTaskHandelEntity setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }
    public String getIpAddress() {
        return ipAddress;
    }
	public SysTaskHandelEntity setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }
    public Boolean getIsEnabled() {
        return isEnabled;
    }
	public SysTaskHandelEntity setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }
    public String getRemark() {
        return remark;
    }
	public SysTaskHandelEntity setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
	public SysTaskHandelEntity setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
	public SysTaskHandelEntity setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}