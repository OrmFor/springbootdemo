package com.jlkj.web.shopnew.core;

public enum StatusCode {

	/** 返回正确 **/
	SUCCESS("0", "success"),

	/** 返回异常 **/
	ERROR("-1", "服务器内部错误"),

	/** 请求相关错误 **/
	ERROR_403("403", "禁止访问"),
	ERROR_404("404", "资源不存在"),
	ERROR_405("405", "请求方法不支持"),
	ERROR_406("406", "地址不存在"),
	ERROR_407("407", "连接客户端失败"),
	ERROR_408("408", "获取凭证失败"),
	ERROR_409("409", "返回为空"),
	ERROR_410("410", "返回状态错误"),
	ERROR_411("411", "访问太过频繁，请稍后重试"),

	/** 程序相关错误 **/
	ERROR_500("500", "程序错误"),
	ERROR_501("501", "支付成功时回调更新状态错误,不用管"),
	ERROR_502("502", "支付失败时回调更新状态错误"),
	ERROR_504("504", "用户未登录"),

	/** 请求头相关错误 **/
	ERROR_TOKEN_EMPTY("600", "缺少token"),
	ERROR_LACK_PARAM("601", "缺少参数"),
	ERROR_PARAM_DECIPHERING("602", "参数解密失败"),
	ERROR_PARAM("603", "参数错误"),
	ERROR_VERSION("604", "缺少version"),

	/** 验证码相关错误 **/
	ERROR_PIC_CODE("700", "图片验证码错误"),
	ERROR_MOBILE_CODE("701", "短信验证码错误"),
	ERROR_MOBILE_CODE_TYPE("702", "短信验证码类型错误"),
	ERROR_MOBILE_CODE_EXPIRED("703", "短信验证码已失效，请重新发送"),
	ERROR_MOBILE_CODE_SEND_LIMIT("704", "短信验证码发送次数过多，请稍后发送"),
	ERROR_MOBILE_CODE_EMPTY("705", "短信验证码为空"),
	ERROR_PWD_SEND_LIMIT("707", "密码错误次数过多,30分钟后再登录"),


	/** 账户相关错误 **/
	ERROR_USER_OTHER_DEVICE_IS_LOGIN("800", "用户另外一台设备登录，请重新登录"),
	ERROR_USER_TOKEN_EXPIRED("801", "用户信息过期，请重新登录"),
	ERROR_USER_TOKEN_DISABLED("802", "用户信息失效，请重新登录"),
	ERROR_OAUTH_TOKEN_NOT_EXISTS("802", "用户未登录"),
	ERROR_MOBILE_NOT_EXISTS("803", "手机号未注册"),
	ERROR_MOBILE_FORMAT("804", "请输入正确的手机号"),
	ERROR_ACCOUNT_LOCK_OR_LEAVE("805", "账户锁定，请联系管理员"),
	ERROR_OLD_PWD_EMPTY("806", "请输入旧密码"),
	ERROR_NEW_PWD_EMPTY("807", "请输入新密码"),
	ERROR_CONFIRM_PWD_EMPTY("808", "请输入确认新密码"),
	ERROR_PWD_NOT_EQUALS("809", "2次密码输入不一致"),
	ERROR_ACCOUNT_NOT_EXIST("810", "账户不存在"),
	ERROR_PWD_VALIDATE("811", "密码校验不通过"),
	ERROR_MOBILE_EMPTY("812", "手机号为空"),
	ERROR_ACCOUNT_EXIST("813", "账户已存在"),
	ERROR_ACCOUNT_LOCK("814", "账户已锁定"),
	ERROR_ACCOUNT_NOT_ACTIVITY("815", "账户未激活"),
	ERROR_ACCOUNT_UPDATE_ERROR("816", "账户更新失败"),
	ERROR_ACCOUNT_PWD_TOO_LONG("817", "密码最长32位"),
	ERROR_ACCOUNT_USER_NAME_FORMAT_ERROR("818", "用户名仅限数字和字母组成，最长32位"),
	ERROR_USER_UPDATE_ERROR("819", "用户更新失败"),
	ERROR_USER_INVITE_UPDATE_ERROR("820", "用户邀请关系更新失败"),
	ERROR_REMARK_EMPTY("821", "备注为空"),
	ERROR_MOVIE_CATEGORY_NOT_EXISTS_NOT_OPEN("822", "该视频分类不存在或者未开放"),
	ERROR_MOVIE_NOT_EXISTS_NOT_PLAY("823", "该视频不存在或者未上架"),
	ERROR_MOVIE_NOT_BUY_OR_EXPIRED("824", "该视频您未购买或者购买已过期"),
	ERROR_NOTIFY_BIND_IP_EMPTY("825", "购买视频回调绑定ip未配置"),
	ERROR_NOTIFY_BIND_IP("826", "非法ip请求"),
	ERROR_PAY_TYPE("827", "支付方式错误"),
	ERROR_BUY_RECORD_NOT_EXISTS("828", "购买记录不存在"),
	ERROR_BUY_RECORD_UPDATE_ERROR("829", "购买更新失败"),
	ERROR_USER_NAME_EXIST("830","用户名已存在"),
	ERROR_USER_EMAIL_EXIST("831","邮箱已注册"),
	ERROR_USER_EMAIL_FORMAT("832","邮箱格式不正确"),
	ERROR_EMAIL_NOT_EXISTS("833","邮箱未注册"),
	ERROR_PWD_EMPTY("851", "密码为空"),
	ERROR_PIC_EMPTY("852", "图形验证码为空"),
	ERROR_PWD_LOGIN("853","密码错误次数大于5次,请在15分钟后重试"),
	ERROR_OAUTH_TOKEN_PROCESS("854", "用户非法认证,请重新登录"),
	ERROR_OWN_CITY("856","非该城市拥有着不能设置课堂名称"),


	/** 消息通知相关错误 **/
	ERROR_NOTIC_NID_EMPTY("900", "NID类型为空"),
	ERROR_NOTIC_TYPE_EMPTY("901", "类型为空"),
	ERROR_NOTIC_RECEIVE_USER_EMPTY("902", "接收者为空"),
	ERROR_NOTIC_RECEIVE_ADDRESS_EMPTY("903", "接收地址为空"),
	ERROR_NOTIC_TYPE_NOT_CONFIG("904", "类型未配置"),

	/** 订单相关错误 **/
	ERROR_ORDER_NOT_EXISTS("1000", "订单不存在"),
	
	/** 签名验证相关错误 **/
	ERROR_ENCRYPT("1100", "加密异常"),
	ERROR_DECRYPT("1101", "解密异常"),
	ERROR_SECRET_KEY("1102", "生成加密秘钥异常"),
	ERROR_VALIDATE_SIGN("1103", "验签异常"),

	/**订单相关错误**/
	ERROR_ORDER_LOCK("2000","城市锁定，不能购买"),
	ERROR_BUYER_WALLETADDRESS_NOT_EXISTS("2001","购买者钱包不存在"),
	ERROR_SELLER_WALLETADDRESS_NOT_EXISTS("2002","未找到出售者钱包,不能设置出售"),
	ERROR_PLAT_WALLETADDRESS_NOT_EXISTS("2003","未找到平台钱包"),
	ERROR_AMOUNT("2004","出售金额不能小于或等于0"),
	ERROR_CTIY_NOT_SELL("2005","CITY NOT FOR SELL"),
	ERROR_CITY_NOT_EDIT("2006","有人正在支付中....."),
	ERROR_TXID_EMPTY("2007","TXID不存在"),
	ERROR_DELETE_ADS_PICTURE("2007","禁止删除"),


	/****/
	ERROR_HAS_COMPANY("3000","该员工已经加入企业"),
	ERROR_HAS_INVITE("3001","该员工已接收邀请"),
	ERROR_NO_USER("3002","用户不存在"),
	ERROR_NO_COMPANY("3003","该用户不存在企业"),
	ERROR_NOT_BOSS("3004","该用户不是BOSS"),
	ERROR_BOSS_HAS_COMPANY("3005","已经创建企业"),
	ERROR_COMPANY_NAME_EXISTS("3006","企业名称已存在"),
	ERROR_HAS_BUSSINESS_CARD("3007","该人员已购买名片"),
	ERROR_HAS_INVITE_PARTNER("3008","已邀请该用户或该用户已接受邀请"),
	ERROR_SUBACCOUNT_NUM("3009","子账号账户数量超限，不能添加"),
	ERROR_NO_STAFF("3010","子账号账户数量超限，不能添加"),
	;

	private String code;
	private String msg;

	StatusCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
