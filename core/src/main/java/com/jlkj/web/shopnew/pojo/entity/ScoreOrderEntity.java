package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class ScoreOrderEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer uid;
	private Integer mgId;
	private Integer orderStatus;
	private String result;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	
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
}