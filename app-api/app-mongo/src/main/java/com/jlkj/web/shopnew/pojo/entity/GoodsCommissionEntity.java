package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class GoodsCommissionEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer inviteId;
	private Integer orderId;
	private Integer goodsId;
	private java.math.BigDecimal goodsPrice;
	private Integer goodsNum;
	private Integer rate;
	private Integer mgId;
	private java.math.BigDecimal money;
	private Integer type;
	private Integer status;
	private Long createTime;
	private Long updateTime;
	
    public Integer getId() {
        return id;
    }
	public GoodsCommissionEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getInviteId() {
        return inviteId;
    }
	public GoodsCommissionEntity setInviteId(Integer inviteId) {
        this.inviteId = inviteId;
        return this;
    }
    public Integer getOrderId() {
        return orderId;
    }
	public GoodsCommissionEntity setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }
    public Integer getGoodsId() {
        return goodsId;
    }
	public GoodsCommissionEntity setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
        return this;
    }
    public java.math.BigDecimal getGoodsPrice() {
        return goodsPrice;
    }
	public GoodsCommissionEntity setGoodsPrice(java.math.BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }
    public Integer getGoodsNum() {
        return goodsNum;
    }
	public GoodsCommissionEntity setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
        return this;
    }
    public Integer getRate() {
        return rate;
    }
	public GoodsCommissionEntity setRate(Integer rate) {
        this.rate = rate;
        return this;
    }
    public Integer getMgId() {
        return mgId;
    }
	public GoodsCommissionEntity setMgId(Integer mgId) {
        this.mgId = mgId;
        return this;
    }
    public java.math.BigDecimal getMoney() {
        return money;
    }
	public GoodsCommissionEntity setMoney(java.math.BigDecimal money) {
        this.money = money;
        return this;
    }
    public Integer getType() {
        return type;
    }
	public GoodsCommissionEntity setType(Integer type) {
        this.type = type;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public GoodsCommissionEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Long getCreateTime() {
        return createTime;
    }
	public GoodsCommissionEntity setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getUpdateTime() {
        return updateTime;
    }
	public GoodsCommissionEntity setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}