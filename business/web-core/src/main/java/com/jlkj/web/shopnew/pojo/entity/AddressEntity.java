package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class AddressEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer uid;
	private String consignee;
	private String phone;
	private String area;
	private String address;
	private Integer createTime;
	private Integer updateTime;
	private Integer addressId;
	private Integer proviceId;
	private Integer cityId;
	private Integer areaId;
	
    public Integer getId() {
        return id;
    }
	public AddressEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getUid() {
        return uid;
    }
	public AddressEntity setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
    public String getConsignee() {
        return consignee;
    }
	public AddressEntity setConsignee(String consignee) {
        this.consignee = consignee;
        return this;
    }
    public String getPhone() {
        return phone;
    }
	public AddressEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getArea() {
        return area;
    }
	public AddressEntity setArea(String area) {
        this.area = area;
        return this;
    }
    public String getAddress() {
        return address;
    }
	public AddressEntity setAddress(String address) {
        this.address = address;
        return this;
    }
    public Integer getCreateTime() {
        return createTime;
    }
	public AddressEntity setCreateTime(Integer createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getUpdateTime() {
        return updateTime;
    }
	public AddressEntity setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getAddressId() {
        return addressId;
    }
	public AddressEntity setAddressId(Integer addressId) {
        this.addressId = addressId;
        return this;
    }
    public Integer getProviceId() {
        return proviceId;
    }
	public AddressEntity setProviceId(Integer proviceId) {
        this.proviceId = proviceId;
        return this;
    }
    public Integer getCityId() {
        return cityId;
    }
	public AddressEntity setCityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }
    public Integer getAreaId() {
        return areaId;
    }
	public AddressEntity setAreaId(Integer areaId) {
        this.areaId = areaId;
        return this;
    }
}