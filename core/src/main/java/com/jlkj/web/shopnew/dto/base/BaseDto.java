package com.jlkj.web.shopnew.dto.base;

/**
 * 
 * @Description 基础参数
 * @author wzq
 * @date 2018年8月16日 下午5:29:42
 */
public class BaseDto {

	private Integer page = 1;// 某页，默认1
	private Integer pageSize = 10;// 每页大小，固定10
	private Integer beginIndex = 0;// sql，limit起始下标

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

}
