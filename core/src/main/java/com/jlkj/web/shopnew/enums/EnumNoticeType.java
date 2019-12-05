package com.jlkj.web.shopnew.enums;
/**
 * 
 * @Description 通知类型枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumNoticeType {

	/** 短信 **/
	TYPE_SMS(1, "短信"),
	/** 邮件 **/
	TYPE_EMAIL(2, "邮件"),
	/** 站内信 **/
	TYPE_MESSAGE(3, "站内信");

	private int type;

	private String name;

	private EnumNoticeType(int type, String name) {
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
