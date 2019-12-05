package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class GoodsEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private Integer catId;
	private Integer mgId;
	private java.math.BigDecimal groupPrice;
	private java.math.BigDecimal price;
	private java.math.BigDecimal oldPrice;
	private java.math.BigDecimal integralPrice;
	private Integer status;
	private Integer totalMount;
	private Integer mount;
	private Integer commissionRate;
	private Integer sort;
	private Integer top;
	private Long createTime;
	private Long updateTime;
	private Integer assemble;
	private Integer assembleNum;
	private Integer integral;
	private Integer integralNum;
	private Integer isHot;
	private Integer isNew;
	private Integer isSeason;
	private Integer isRecommend;
	private Integer isIntegral;
	private Integer isRecIntegral;
	private Integer goodsType;
	private Integer isHotPm;
	private Integer isNewPm;
	private Integer isJinlieHot;
	private Integer isJinlieRec;
	private String describee;
	private Integer examineStatus;
	private Integer limitedStatus;
	private Integer limitedType;
	private Integer limitedNum;
	private java.math.BigDecimal freightFree;
	private Integer integralShop;
	private Integer isMainProduct;
	private java.math.BigDecimal specialPrice;
	private Integer isshare;
	
    public Integer getId() {
        return id;
    }
	public GoodsEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getTitle() {
        return title;
    }
	public GoodsEntity setTitle(String title) {
        this.title = title;
        return this;
    }
    public Integer getCatId() {
        return catId;
    }
	public GoodsEntity setCatId(Integer catId) {
        this.catId = catId;
        return this;
    }
    public Integer getMgId() {
        return mgId;
    }
	public GoodsEntity setMgId(Integer mgId) {
        this.mgId = mgId;
        return this;
    }
    public java.math.BigDecimal getGroupPrice() {
        return groupPrice;
    }
	public GoodsEntity setGroupPrice(java.math.BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
        return this;
    }
    public java.math.BigDecimal getPrice() {
        return price;
    }
	public GoodsEntity setPrice(java.math.BigDecimal price) {
        this.price = price;
        return this;
    }
    public java.math.BigDecimal getOldPrice() {
        return oldPrice;
    }
	public GoodsEntity setOldPrice(java.math.BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }
    public java.math.BigDecimal getIntegralPrice() {
        return integralPrice;
    }
	public GoodsEntity setIntegralPrice(java.math.BigDecimal integralPrice) {
        this.integralPrice = integralPrice;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public GoodsEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getTotalMount() {
        return totalMount;
    }
	public GoodsEntity setTotalMount(Integer totalMount) {
        this.totalMount = totalMount;
        return this;
    }
    public Integer getMount() {
        return mount;
    }
	public GoodsEntity setMount(Integer mount) {
        this.mount = mount;
        return this;
    }
    public Integer getCommissionRate() {
        return commissionRate;
    }
	public GoodsEntity setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
        return this;
    }
    public Integer getSort() {
        return sort;
    }
	public GoodsEntity setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public Integer getTop() {
        return top;
    }
	public GoodsEntity setTop(Integer top) {
        this.top = top;
        return this;
    }
    public Long getCreateTime() {
        return createTime;
    }
	public GoodsEntity setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getUpdateTime() {
        return updateTime;
    }
	public GoodsEntity setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getAssemble() {
        return assemble;
    }
	public GoodsEntity setAssemble(Integer assemble) {
        this.assemble = assemble;
        return this;
    }
    public Integer getAssembleNum() {
        return assembleNum;
    }
	public GoodsEntity setAssembleNum(Integer assembleNum) {
        this.assembleNum = assembleNum;
        return this;
    }
    public Integer getIntegral() {
        return integral;
    }
	public GoodsEntity setIntegral(Integer integral) {
        this.integral = integral;
        return this;
    }
    public Integer getIntegralNum() {
        return integralNum;
    }
	public GoodsEntity setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
        return this;
    }
    public Integer getIsHot() {
        return isHot;
    }
	public GoodsEntity setIsHot(Integer isHot) {
        this.isHot = isHot;
        return this;
    }
    public Integer getIsNew() {
        return isNew;
    }
	public GoodsEntity setIsNew(Integer isNew) {
        this.isNew = isNew;
        return this;
    }
    public Integer getIsSeason() {
        return isSeason;
    }
	public GoodsEntity setIsSeason(Integer isSeason) {
        this.isSeason = isSeason;
        return this;
    }
    public Integer getIsRecommend() {
        return isRecommend;
    }
	public GoodsEntity setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
        return this;
    }
    public Integer getIsIntegral() {
        return isIntegral;
    }
	public GoodsEntity setIsIntegral(Integer isIntegral) {
        this.isIntegral = isIntegral;
        return this;
    }
    public Integer getIsRecIntegral() {
        return isRecIntegral;
    }
	public GoodsEntity setIsRecIntegral(Integer isRecIntegral) {
        this.isRecIntegral = isRecIntegral;
        return this;
    }
    public Integer getGoodsType() {
        return goodsType;
    }
	public GoodsEntity setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
        return this;
    }
    public Integer getIsHotPm() {
        return isHotPm;
    }
	public GoodsEntity setIsHotPm(Integer isHotPm) {
        this.isHotPm = isHotPm;
        return this;
    }
    public Integer getIsNewPm() {
        return isNewPm;
    }
	public GoodsEntity setIsNewPm(Integer isNewPm) {
        this.isNewPm = isNewPm;
        return this;
    }
    public Integer getIsJinlieHot() {
        return isJinlieHot;
    }
	public GoodsEntity setIsJinlieHot(Integer isJinlieHot) {
        this.isJinlieHot = isJinlieHot;
        return this;
    }
    public Integer getIsJinlieRec() {
        return isJinlieRec;
    }
	public GoodsEntity setIsJinlieRec(Integer isJinlieRec) {
        this.isJinlieRec = isJinlieRec;
        return this;
    }
    public String getDescribee() {
        return describee;
    }
	public GoodsEntity setDescribee(String describee) {
        this.describee = describee;
        return this;
    }
    public Integer getExamineStatus() {
        return examineStatus;
    }
	public GoodsEntity setExamineStatus(Integer examineStatus) {
        this.examineStatus = examineStatus;
        return this;
    }
    public Integer getLimitedStatus() {
        return limitedStatus;
    }
	public GoodsEntity setLimitedStatus(Integer limitedStatus) {
        this.limitedStatus = limitedStatus;
        return this;
    }
    public Integer getLimitedType() {
        return limitedType;
    }
	public GoodsEntity setLimitedType(Integer limitedType) {
        this.limitedType = limitedType;
        return this;
    }
    public Integer getLimitedNum() {
        return limitedNum;
    }
	public GoodsEntity setLimitedNum(Integer limitedNum) {
        this.limitedNum = limitedNum;
        return this;
    }
    public java.math.BigDecimal getFreightFree() {
        return freightFree;
    }
	public GoodsEntity setFreightFree(java.math.BigDecimal freightFree) {
        this.freightFree = freightFree;
        return this;
    }
    public Integer getIntegralShop() {
        return integralShop;
    }
	public GoodsEntity setIntegralShop(Integer integralShop) {
        this.integralShop = integralShop;
        return this;
    }
    public Integer getIsMainProduct() {
        return isMainProduct;
    }
	public GoodsEntity setIsMainProduct(Integer isMainProduct) {
        this.isMainProduct = isMainProduct;
        return this;
    }
    public java.math.BigDecimal getSpecialPrice() {
        return specialPrice;
    }
	public GoodsEntity setSpecialPrice(java.math.BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
        return this;
    }
    public Integer getIsshare() {
        return isshare;
    }
	public GoodsEntity setIsshare(Integer isshare) {
        this.isshare = isshare;
        return this;
    }
}