package com.jlkj.web.shopnew.enums;
/**
 * 
 * @Description 通知是否发送枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumNoticeSend {

	/** 不发送 **/
	TYPE_UNSEND(0, "不发送"),
	/** 发送 **/
	TYPE_SEND(1, "发送");

	private int type;

	private String name;

	private EnumNoticeSend(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
