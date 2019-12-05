package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysTaskLogEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String moduleName;
	private String hostname;
	private String ipAddress;
	private Boolean isSuccess;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updatedAt;
	
    public Integer getId() {
        return id;
    }
	public SysTaskLogEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getModuleName() {
        return moduleName;
    }
	public SysTaskLogEntity setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }
    public String getHostname() {
        return hostname;
    }
	public SysTaskLogEntity setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }
    public String getIpAddress() {
        return ipAddress;
    }
	public SysTaskLogEntity setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }
    public Boolean getIsSuccess() {
        return isSuccess;
    }
	public SysTaskLogEntity setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
	public SysTaskLogEntity setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }
	public SysTaskLogEntity setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}