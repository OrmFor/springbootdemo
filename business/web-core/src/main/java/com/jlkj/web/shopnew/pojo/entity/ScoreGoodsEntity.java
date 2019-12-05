package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class ScoreGoodsEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer goodsId;
	private String goodsName;
	private String qrImgPath;
	private java.math.BigDecimal score;
	private java.math.BigDecimal costPrice;
	private java.math.BigDecimal goodsCurrentPrice;
	private java.math.BigDecimal selfPrice;
	private Integer salesCount;
	private Integer goodsStatus;
	private String result;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
    public Integer getId() {
        return id;
    }
	public ScoreGoodsEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getGoodsId() {
        return goodsId;
    }
	public ScoreGoodsEntity setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
        return this;
    }
    public String getGoodsName() {
        return goodsName;
    }
	public ScoreGoodsEntity setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }
    public String getQrImgPath() {
        return qrImgPath;
    }
	public ScoreGoodsEntity setQrImgPath(String qrImgPath) {
        this.qrImgPath = qrImgPath;
        return this;
    }
    public java.math.BigDecimal getScore() {
        return score;
    }
	public ScoreGoodsEntity setScore(java.math.BigDecimal score) {
        this.score = score;
        return this;
    }
    public java.math.BigDecimal getCostPrice() {
        return costPrice;
    }
	public ScoreGoodsEntity setCostPrice(java.math.BigDecimal costPrice) {
        this.costPrice = costPrice;
        return this;
    }
    public java.math.BigDecimal getGoodsCurrentPrice() {
        return goodsCurrentPrice;
    }
	public ScoreGoodsEntity setGoodsCurrentPrice(java.math.BigDecimal goodsCurrentPrice) {
        this.goodsCurrentPrice = goodsCurrentPrice;
        return this;
    }
    public java.math.BigDecimal getSelfPrice() {
        return selfPrice;
    }
	public ScoreGoodsEntity setSelfPrice(java.math.BigDecimal selfPrice) {
        this.selfPrice = selfPrice;
        return this;
    }
    public Integer getSalesCount() {
        return salesCount;
    }
	public ScoreGoodsEntity setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
        return this;
    }
    public Integer getGoodsStatus() {
        return goodsStatus;
    }
	public ScoreGoodsEntity setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
        return this;
    }
    public String getResult() {
        return result;
    }
	public ScoreGoodsEntity setResult(String result) {
        this.result = result;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public ScoreGoodsEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public ScoreGoodsEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}