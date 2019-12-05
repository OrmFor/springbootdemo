package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class LogEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer uid;
	private Integer operationId;
	private Integer type;
	private String desc;
	private Integer status;
	private Integer num;
	private Integer addTime;
	private Long createTime;
	private Long updateTime;
	private String goodsName;
	
    public Integer getId() {
        return id;
    }
	public LogEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getUid() {
        return uid;
    }
	public LogEntity setUid(Integer uid) {
        this.uid = uid;
        return this;
    }
    public Integer getOperationId() {
        return operationId;
    }
	public LogEntity setOperationId(Integer operationId) {
        this.operationId = operationId;
        return this;
    }
    public Integer getType() {
        return type;
    }
	public LogEntity setType(Integer type) {
        this.type = type;
        return this;
    }
    public String getDesc() {
        return desc;
    }
	public LogEntity setDesc(String desc) {
        this.desc = desc;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public LogEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
    public Integer getNum() {
        return num;
    }
	public LogEntity setNum(Integer num) {
        this.num = num;
        return this;
    }
    public Integer getAddTime() {
        return addTime;
    }
	public LogEntity setAddTime(Integer addTime) {
        this.addTime = addTime;
        return this;
    }
    public Long getCreateTime() {
        return createTime;
    }
	public LogEntity setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }
    public Long getUpdateTime() {
        return updateTime;
    }
	public LogEntity setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public String getGoodsName() {
        return goodsName;
    }
	public LogEntity setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }
}