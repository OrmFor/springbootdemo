package com.jlkj.web.shopnew.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class AreaNewEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String areaName;
	private Integer parentId;
	private Integer yqsAreaId;
	private String level;
	
    public Integer getId() {
        return id;
    }
	public AreaNewEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getAreaName() {
        return areaName;
    }
	public AreaNewEntity setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }
	public AreaNewEntity setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public Integer getYqsAreaId() {
        return yqsAreaId;
    }
	public AreaNewEntity setYqsAreaId(Integer yqsAreaId) {
        this.yqsAreaId = yqsAreaId;
        return this;
    }
    public String getLevel() {
        return level;
    }
	public AreaNewEntity setLevel(String level) {
        this.level = level;
        return this;
    }
}