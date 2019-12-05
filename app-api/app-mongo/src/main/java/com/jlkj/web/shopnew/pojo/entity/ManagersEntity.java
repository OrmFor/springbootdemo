package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class ManagersEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer pMgId;
	private Integer level;
	private String phone;
	private String name;
	private String pwd;
	private Integer platRate;
	private Integer roleId;
	private Integer pCate;
	private Integer categoryId;
	private Integer uid;
	private Integer comId;
	private String email;
	private Integer status;
	private Long createTime;
	private Long updateTime;
	private Integer type;
	private String longitude;
	private String latitude;
	private String address;
	private String imgUrl;
	private Integer province;
	private Integer city;
	private Integer area;
	private Integer isNew;
	private Integer isWeekend;
	private java.math.BigDecimal integral;
	private String label;
	private Integer isDiscountZone;
	private java.math.BigDecimal startFree;
	private Integer saleNum;
	private Integer source;
	private String qualificationsImg;
	private Integer mallTop;
	private Integer tempType;
	private String payCode;
	private java.math.BigDecimal scanCommission;
	private String mchId;
	private String key;
	private String path;
	private java.math.BigDecimal deductionIntegral;
	
    public Integer getId() {
        return id;
    }
	public ManagersEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPmgId() {
        return pMgId;
    }

    public Integer getpCate() {
        return pCate;
    }

    public void setpCate(Integer pCate) {
        this.pCate = pCate;
    }

    public void setPmgId(Integer pMgId) {
        this.pMgId = pMgId;
    }

    public Integer getLevel() {
        return level;
    }
	public ManagersEntity setLevel(Integer level) {
        this.level = level;
        return this;
    }
    public String getPhone() {
        return phone;
    }
	public ManagersEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getName() {
        return name;
    }
	public ManagersEntity setName(String name) {
        this.name = name;
        return this;
    }
    public String getPwd() {
        return pwd;
    }
	public ManagersEntity setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }
    public Integer getPlatRate() {
        return platRate;
    }
	public ManagersEntity setPlatRate(Integer platRate) {
        this.platRate = platRate;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }
	public ManagersEntity setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
	public ManagersEntity setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }
    public Integer getUid() {
        return uid;
    }
	public ManagersEntity setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
    public Integer getComId() {
        return comId;
    }
	public ManagersEntity setComId(Integer comId) {
        this.comId = comId;
        return this;
    }
    public String getEmail() {
        return email;
    }
	public ManagersEntity setEmail(String email) {
        this.email = email;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public ManagersEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Long getCreateTime() {
        return createTime;
    }
	public ManagersEntity setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getUpdateTime() {
        return updateTime;
    }
	public ManagersEntity setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public Integer getType() {
        return type;
    }
	public ManagersEntity setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getLongitude() {
        return longitude;
    }
	public ManagersEntity setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
    public String getLatitude() {
        return latitude;
    }
	public ManagersEntity setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }
    public String getAddress() {
        return address;
    }
	public ManagersEntity setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getImgUrl() {
        return imgUrl;
    }
	public ManagersEntity setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }
    public Integer getProvince() {
        return province;
    }
	public ManagersEntity setProvince(Integer province) {
        this.province = province;
        return this;
    }
    public Integer getCity() {
        return city;
    }
	public ManagersEntity setCity(Integer city) {
        this.city = city;
        return this;
    }
    public Integer getArea() {
        return area;
    }
	public ManagersEntity setArea(Integer area) {
        this.area = area;
        return this;
    }
    public Integer getIsNew() {
        return isNew;
    }
	public ManagersEntity setIsNew(Integer isNew) {
        this.isNew = isNew;
        return this;
    }
    public Integer getIsWeekend() {
        return isWeekend;
    }
	public ManagersEntity setIsWeekend(Integer isWeekend) {
        this.isWeekend = isWeekend;
        return this;
    }
    public java.math.BigDecimal getIntegral() {
        return integral;
    }
	public ManagersEntity setIntegral(java.math.BigDecimal integral) {
        this.integral = integral;
        return this;
    }
    public String getLabel() {
        return label;
    }
	public ManagersEntity setLabel(String label) {
        this.label = label;
        return this;
    }
    public Integer getIsDiscountZone() {
        return isDiscountZone;
    }
	public ManagersEntity setIsDiscountZone(Integer isDiscountZone) {
        this.isDiscountZone = isDiscountZone;
        return this;
    }
    public java.math.BigDecimal getStartFree() {
        return startFree;
    }
	public ManagersEntity setStartFree(java.math.BigDecimal startFree) {
        this.startFree = startFree;
        return this;
    }
    public Integer getSaleNum() {
        return saleNum;
    }
	public ManagersEntity setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
        return this;
    }
    public Integer getSource() {
        return source;
    }
	public ManagersEntity setSource(Integer source) {
        this.source = source;
        return this;
    }
    public String getQualificationsImg() {
        return qualificationsImg;
    }
	public ManagersEntity setQualificationsImg(String qualificationsImg) {
        this.qualificationsImg = qualificationsImg;
        return this;
    }
    public Integer getMallTop() {
        return mallTop;
    }
	public ManagersEntity setMallTop(Integer mallTop) {
        this.mallTop = mallTop;
        return this;
    }
    public Integer getTempType() {
        return tempType;
    }
	public ManagersEntity setTempType(Integer tempType) {
        this.tempType = tempType;
        return this;
    }
    public String getPayCode() {
        return payCode;
    }
	public ManagersEntity setPayCode(String payCode) {
        this.payCode = payCode;
        return this;
    }
    public java.math.BigDecimal getScanCommission() {
        return scanCommission;
    }
	public ManagersEntity setScanCommission(java.math.BigDecimal scanCommission) {
        this.scanCommission = scanCommission;
        return this;
    }
    public String getMchId() {
        return mchId;
    }
	public ManagersEntity setMchId(String mchId) {
        this.mchId = mchId;
        return this;
    }
    public String getKey() {
        return key;
    }
	public ManagersEntity setKey(String key) {
        this.key = key;
        return this;
    }
    public String getPath() {
        return path;
    }
	public ManagersEntity setPath(String path) {
        this.path = path;
        return this;
    }
    public java.math.BigDecimal getDeductionIntegral() {
        return deductionIntegral;
    }
	public ManagersEntity setDeductionIntegral(java.math.BigDecimal deductionIntegral) {
        this.deductionIntegral = deductionIntegral;
        return this;
    }
}