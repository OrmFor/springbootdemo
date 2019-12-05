package com.jlkj.web.shopnew.enums;

/**
 * 
 * @Description 通知NID类型枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumNoticeNid {

	/** 注册验证码 **/
	NID_REGISTER("register", "注册验证码"),
	/** 修改登录密码 **/
	NID_MODIFY_PASSWORD("modify_password", "修改登录密码"),
	/** 找回密码 **/
	NID_RESET_PASSWORD("reset_password", "找回密码"),
	/**邮箱认证**/
	NID_EMAIL_OAUTH("eamil_oauth","邮箱认证"),
	/** 快捷登录 **/
	NID_QUICK_LOGIN("quick_login", "快捷登录"),
	/** 点赞通知**/
	GIVE_A_LIKE("give_a_like","点赞"),
	/**留言通知**/
	LEAVE_A_MESSAGE("leave_a_message","留言"),

	/**购买成功通知**/
	SUCCESS_BUY("success_buy","购买成功"),

	PICTURE_LIKE("pictrue_like","图片点赞"),

	/**黑名单禁言相关通知**/
	CHAT_CONTENT_HIDDEN("chat_content_hidden","聊天内容隐藏"),
	ADS_PICTURE_HIDDEN("ads_picture_hidden","广告图片隐藏"),
	CITY_PICTURE_HIDDEN("city_picture_hidden","城市图片隐藏"),
	PERMISSION_STOP("permission_stop","权限禁用"),


		;
	private String nid;

	private String name;

	private EnumNoticeNid(String nid, String name) {
		this.nid = nid;
		this.name = name;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
