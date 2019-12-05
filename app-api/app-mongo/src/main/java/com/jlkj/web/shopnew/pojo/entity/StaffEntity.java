package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class StaffEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer staffId;
	private Integer companyId;
	private String staffPic;
	private String staffName;
	private String staffPosition;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private Integer role;
	private Integer acceptMessage;
	private Integer partnerLevel;
	private Integer isServicep;
	private Integer servicepLevel;
	private String serviceProviderAddress;
	private Integer servicepProvice;
	private Integer servicepCity;
	private Integer servicepArea;
	
    public Integer getId() {
        return id;
    }
	public StaffEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getStaffId() {
        return staffId;
    }
	public StaffEntity setStaffId(Integer staffId) {
        this.staffId = staffId;
        return this;
    }
    public Integer getCompanyId() {
        return companyId;
    }
	public StaffEntity setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }
    public String getStaffPic() {
        return staffPic;
    }
	public StaffEntity setStaffPic(String staffPic) {
        this.staffPic = staffPic;
        return this;
    }
    public String getStaffName() {
        return staffName;
    }
	public StaffEntity setStaffName(String staffName) {
        this.staffName = staffName;
        return this;
    }
    public String getStaffPosition() {
        return staffPosition;
    }
	public StaffEntity setStaffPosition(String staffPosition) {
        this.staffPosition = staffPosition;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public StaffEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public StaffEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getRole() {
        return role;
    }
	public StaffEntity setRole(Integer role) {
        this.role = role;
        return this;
    }
    public Integer getAcceptMessage() {
        return acceptMessage;
    }
	public StaffEntity setAcceptMessage(Integer acceptMessage) {
        this.acceptMessage = acceptMessage;
        return this;
    }
    public Integer getPartnerLevel() {
        return partnerLevel;
    }
	public StaffEntity setPartnerLevel(Integer partnerLevel) {
        this.partnerLevel = partnerLevel;
        return this;
    }
    public Integer getIsServicep() {
        return isServicep;
    }
	public StaffEntity setIsServicep(Integer isServicep) {
        this.isServicep = isServicep;
        return this;
    }
    public Integer getServicepLevel() {
        return servicepLevel;
    }
	public StaffEntity setServicepLevel(Integer servicepLevel) {
        this.servicepLevel = servicepLevel;
        return this;
    }
    public String getServiceProviderAddress() {
        return serviceProviderAddress;
    }
	public StaffEntity setServiceProviderAddress(String serviceProviderAddress) {
        this.serviceProviderAddress = serviceProviderAddress;
        return this;
    }
    public Integer getServicepProvice() {
        return servicepProvice;
    }
	public StaffEntity setServicepProvice(Integer servicepProvice) {
        this.servicepProvice = servicepProvice;
        return this;
    }
    public Integer getServicepCity() {
        return servicepCity;
    }
	public StaffEntity setServicepCity(Integer servicepCity) {
        this.servicepCity = servicepCity;
        return this;
    }
    public Integer getServicepArea() {
        return servicepArea;
    }
	public StaffEntity setServicepArea(Integer servicepArea) {
        this.servicepArea = servicepArea;
        return this;
    }
}