package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class ScoreOrderEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer uid;
	private Integer mgId;
	private String selfOrderNum;
	private String orderNum;
	private Integer orderStatus;
	private String result;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private java.math.BigDecimal orderPrice;
	private java.math.BigDecimal orderIntegralPrice;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date payTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date receiveTime;
	private String payId;
	
    public Integer getId() {
        return id;
    }
	public ScoreOrderEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getUid() {
        return uid;
    }
	public ScoreOrderEntity setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
    public Integer getMgId() {
        return mgId;
    }
	public ScoreOrderEntity setMgId(Integer mgId) {
        this.mgId = mgId;
        return this;
    }
    public String getSelfOrderNum() {
        return selfOrderNum;
    }
	public ScoreOrderEntity setSelfOrderNum(String selfOrderNum) {
        this.selfOrderNum = selfOrderNum;
        return this;
    }
    public String getOrderNum() {
        return orderNum;
    }
	public ScoreOrderEntity setOrderNum(String orderNum) {
        this.orderNum = orderNum;
        return this;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }
	public ScoreOrderEntity setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
    public String getResult() {
        return result;
    }
	public ScoreOrderEntity setResult(String result) {
        this.result = result;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public ScoreOrderEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public ScoreOrderEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public java.math.BigDecimal getOrderPrice() {
        return orderPrice;
    }
	public ScoreOrderEntity setOrderPrice(java.math.BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }
    public java.math.BigDecimal getOrderIntegralPrice() {
        return orderIntegralPrice;
    }
	public ScoreOrderEntity setOrderIntegralPrice(java.math.BigDecimal orderIntegralPrice) {
        this.orderIntegralPrice = orderIntegralPrice;
        return this;
    }
    public String getRemark() {
        return remark;
    }
	public ScoreOrderEntity setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public java.util.Date getPayTime() {
        return payTime;
    }
	public ScoreOrderEntity setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
        return this;
    }
    public java.util.Date getReceiveTime() {
        return receiveTime;
    }
	public ScoreOrderEntity setReceiveTime(java.util.Date receiveTime) {
        this.receiveTime = receiveTime;
        return this;
    }
    public String getPayId() {
        return payId;
    }
	public ScoreOrderEntity setPayId(String payId) {
        this.payId = payId;
        return this;
    }
}