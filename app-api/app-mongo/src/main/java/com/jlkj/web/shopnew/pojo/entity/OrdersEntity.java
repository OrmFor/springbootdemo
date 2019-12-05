package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class OrdersEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer orderGroupId;
	private String sn;
	private String goodsId;
	private String specPriceId;
	private String mgId;
	private String addressId;
	private java.math.BigDecimal price;
	private java.math.BigDecimal integralPrice;
	private String mount;
	private Integer status;
	private Integer uid;
	private Integer isCommission;
	private Integer type;
	private String remarks;
	private Integer createTime;
	private Integer updateTime;
	private Integer payTime;
	private Integer groupId;
	private Integer payStatus;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer isEval;
	private Integer orderType;
	private Integer isPickUp;
	private Integer isScan;
	private Integer selfMchid;
	private java.math.BigDecimal realPrice;
	
    public Integer getId() {
        return id;
    }
	public OrdersEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getOrderGroupId() {
        return orderGroupId;
    }
	public OrdersEntity setOrderGroupId(Integer orderGroupId) {
        this.orderGroupId = orderGroupId;
        return this;
    }
    public String getSn() {
        return sn;
    }
	public OrdersEntity setSn(String sn) {
        this.sn = sn;
        return this;
    }
    public String getGoodsId() {
        return goodsId;
    }
	public OrdersEntity setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }
    public String getSpecPriceId() {
        return specPriceId;
    }
	public OrdersEntity setSpecPriceId(String specPriceId) {
        this.specPriceId = specPriceId;
        return this;
    }
    public String getMgId() {
        return mgId;
    }
	public OrdersEntity setMgId(String mgId) {
        this.mgId = mgId;
        return this;
    }
    public String getAddressId() {
        return addressId;
    }
	public OrdersEntity setAddressId(String addressId) {
        this.addressId = addressId;
        return this;
    }
    public java.math.BigDecimal getPrice() {
        return price;
    }
	public OrdersEntity setPrice(java.math.BigDecimal price) {
        this.price = price;
        return this;
    }
    public java.math.BigDecimal getIntegralPrice() {
        return integralPrice;
    }
	public OrdersEntity setIntegralPrice(java.math.BigDecimal integralPrice) {
        this.integralPrice = integralPrice;
        return this;
    }
    public String getMount() {
        return mount;
    }
	public OrdersEntity setMount(String mount) {
        this.mount = mount;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public OrdersEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getUid() {
        return uid;
    }
	public OrdersEntity setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
    public Integer getIsCommission() {
        return isCommission;
    }
	public OrdersEntity setIsCommission(Integer isCommission) {
        this.isCommission = isCommission;
        return this;
    }
    public Integer getType() {
        return type;
    }
	public OrdersEntity setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getRemarks() {
        return remarks;
    }
	public OrdersEntity setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }
    public Integer getCreateTime() {
        return createTime;
    }
	public OrdersEntity setCreateTime(Integer createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getUpdateTime() {
        return updateTime;
    }
	public OrdersEntity setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getPayTime() {
        return payTime;
    }
	public OrdersEntity setPayTime(Integer payTime) {
        this.payTime = payTime;
        return this;
    }
    public Integer getGroupId() {
        return groupId;
    }
	public OrdersEntity setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }
    public Integer getPayStatus() {
        return payStatus;
    }
	public OrdersEntity setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
        return this;
    }
    public Integer getYear() {
        return year;
    }
	public OrdersEntity setYear(Integer year) {
        this.year = year;
        return this;
    }
    public Integer getMonth() {
        return month;
    }
	public OrdersEntity setMonth(Integer month) {
        this.month = month;
        return this;
    }
    public Integer getDay() {
        return day;
    }
	public OrdersEntity setDay(Integer day) {
        this.day = day;
        return this;
    }
    public Integer getIsEval() {
        return isEval;
    }
	public OrdersEntity setIsEval(Integer isEval) {
        this.isEval = isEval;
        return this;
    }
    public Integer getOrderType() {
        return orderType;
    }
	public OrdersEntity setOrderType(Integer orderType) {
        this.orderType = orderType;
        return this;
    }
    public Integer getIsPickUp() {
        return isPickUp;
    }
	public OrdersEntity setIsPickUp(Integer isPickUp) {
        this.isPickUp = isPickUp;
        return this;
    }
    public Integer getIsScan() {
        return isScan;
    }
	public OrdersEntity setIsScan(Integer isScan) {
        this.isScan = isScan;
        return this;
    }
    public Integer getSelfMchid() {
        return selfMchid;
    }
	public OrdersEntity setSelfMchid(Integer selfMchid) {
        this.selfMchid = selfMchid;
        return this;
    }
    public java.math.BigDecimal getRealPrice() {
        return realPrice;
    }
	public OrdersEntity setRealPrice(java.math.BigDecimal realPrice) {
        this.realPrice = realPrice;
        return this;
    }
}