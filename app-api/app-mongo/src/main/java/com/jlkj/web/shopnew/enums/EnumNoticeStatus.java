package com.jlkj.web.shopnew.enums;
/**
 * 
 * @Description 通知发送状态枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumNoticeStatus {

	/** 未发送 **/
	STATUS_UNSEND(0, "未发送"),
	/** 已发送 **/
	STATUS_SEND(1, "已发送");

	private int status;

	private String name;

	private EnumNoticeStatus(int status, String name) {
		this.status = status;
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
