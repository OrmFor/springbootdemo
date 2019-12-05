package com.jlkj.web.shopnew.view.base;

/**
 * 
 * @Description 分页返回
 * @author wwy
 * @date 2018年8月16日 下午5:29:42
 */
public class PageView {

	private Object result;// 分页结果集
	private Integer totalRow;// 总数

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Integer getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
	}

}
