package com.jlkj.web.shopnew.constant;
/**
 * 消息模板常量类
 * 
 * @author xx	
 * @version 2.0
 * @since 2014年3月26日
 */
public class NoticeConstant {

	/** 邮箱认证-发送认证链接 **/
	public static final String NOTICE_EMAIL_ACTIVE = "get_email";
	/** 通过邮箱找回密码-发送验证码 **/
	public static final String NOTICE_GET_PWD_EMAIL = "get_pwd_email";
	/** 通过手机找回密码-发送验证码 **/
	public static final String NOTICE_GET_PWD_PHONE = "get_pwd_phone";
	/** 绑定邮箱-发送验证码 **/
	public static final String NOTICE_BIND_EMAIL = "bind_email";
	/** 修改绑定邮箱-发送验证码 **/
	public static final String NOTICE_MODIFY_EMAIL = "modify_email";
	/** 绑定手机-发送验证码 **/
	public static final String NOTICE_BIND_PHONE = "bind_phone";
	/** 修改绑定手机-发送验证码 **/
	public static final String NOTICE_MODIFY_PHONE = "modify_phone";
	/** 找回交易密码 **/
	public static final String NOTICE_GET_PAYPWD = "get_paypwd";
	/** 新借款标发布 **/
	public static final String NEW_BORROW = "new_borrow";
	/** 借款标取消 **/
	public static final String BORROW_CANCEL = "borrow_cancel";
	/** 后台初审通过 **/
	public static final String BORROW_VERIFY_SUCC = "borrow_verify_succ";
	/** 后台初审不通过 **/
	public static final String BORROW_VERIFY_FAIL = "borrow_verify_fail";
	/** 投标成功 **/
	public static final String INVEST_SUCC = "invest_succ";
	/** 投标失败 **/
	public static final String INVEST_FAIL = "invest_fail";
	/** 自动投标成功 **/
	public static final String AUTO_TENDER = "auto_tender";
	/** 满标审核通过 **/
	public static final String BORROW_FULL_SUCC = "borrow_full_succ";
	/** 满标审核失败 **/
	public static final String BORROW_FULL_FAIL = "borrow_full_fail";
	/** 满标审核失败投资人 **/
	public static final String BORROW_FULL_FAIL_TENDER = "borrow_full_fail_tender";
	/** 标的撤回 **/
	public static final String BORROW_FULL_BACK = "borrow_full_back";
	/** 借款人还款 **/
	public static final String RECEIVE_REPAY = "receive_repay";
	/** 还款成功 **/
	public static final String REPAY_SUCC = "repay_succ";
	/** 代偿成功 **/
    public static final String COMPENSATE_SUCC = "compensate_succ";
	/** 收到投标奖励 **/
	public static final String RECEIVE_TENDER_AWARD = "receive_tender_award";
	/** 支付投标奖励 **/
	public static final String DEDUCT_BORROWER_AWARD = "deduct_borrower_award";
	/** 线上充值成功 **/
	public static final String RECHARGE_SUCC = "recharge_succ";
	/** 登录密码修改 **/
	public static final String PASSWORD_UPDATE = "password_update";
	/** 交易密码修改 **/
	public static final String PAYPWD_UPDATE = "paypwd_update";
	/** 还款提前通知 **/
    public static final String BORROWER_REPAY_NOTICE = "borrower_repay_notice";
    /** 后台扣款通知 **/
    public static final String HOUTAI_DEDUCT_SUCC = "houtai_deduct_succ";
    /** 认证通过 **/
    public static final String CERTIFY_SUCC = "certify_succ";
    /** 认证未通过 **/
    public static final String CERTIFY_FAIL = "certify_fail";
    /** 债权转让成功出让人 **/
    public static final String BOND_SELL_SUCC = "bond_sell_succ";
    /** 债权转让成功受让人 **/
    public static final String BOND_BUY_SUCC = "bond_buy_succ";
    /** 债权转让成功受让人 **/
    public static final String BOND_NEW_SUCC = "bond_new_succ";
	/** 债权投资人发送还款成功通知 **/
	public static final String BOND_RECEIVE_SUCC = "bond_receive_succ";
    /** 债权转让撤回出让人 **/
    public static final String BOND_SELL_STOP = "bond_sell_stop";
    /** 积分兑换审核不通过 **/
    public static final String SCORE_CONVERT_FAIL = "score_convert_fail";
    /** 积分商城发货通知 **/
    public static final String SCORE_DELIVERY_SUCC = "score_delivery_succ";
    
    public static final String WIDTH_DRAW_SUCCESS = "cash_verify_succ";
    /** 用户申请提现 **/
	public static final String APPLY_CASH = "apply_cash";
	public static final String APPLY_CASH_VERIFY_FAIL = "apply_cash_verify_fail";
	public static final String APPLY_CASH_BANK_FAIL = "apply_cash_bank_fail";

    /** 注册验证码 **/
    public static final String NOTICE_REG = "notice_reg";
    public static final String NOTICE_SUB_ACCOUNT_ADD="notice_sub_account_add";

    /**注册提交**/
    public static final String REG_SUBMIT="reg_submit";
    
    public static final String NOTICE_INVEST_VALID = "notice_invest_valid";

    /**营业执照ocr识别**/
    public static final String OCR_BUSINESS_LICENSE="ocr_business_license";

    /**登陆**/
    public static final String LOGIN_SUBMIT="login_submit";

    /** 发送手机验证码*/
    public static final String PHONE_SEND = "mobilePhone";
    
	/** 活动发送手机验证码 */
	public static final String ACTIVITY_PHONE_SEND = "activityMobilePhone";

    /** 发送邮箱验证码*/
    public static final String EMAIL_SEND = "email";

    /**企业审核失败*/
	public static final String COMPANY_AUDIT_FAIL = "apply_audit_fail";

	/**企业审核成功*/
	public static final String COMPANY_AUDIT_SUCCESS = "apply_audit_success";

	public static final String AGENT_AUDIT_SUCCESS = "agent_audit_success";

	public static final String AGENT_AUDIT_FAIL = "agent_audit_fail";


	/**子账户创建成功*/
	public static final String SUB_ACCOUNT_ADD_SUCCESS="sub_account_add_success";


	public static final byte NOTICE_SEND = 1;
	public static final byte NOTICE_NOT_SEND = 0;
	public static final byte NOTICE_RECEIVE = 1;
	public static final byte NOTICE_NOT_RECEIVE = 0;
	public static final int NOTICE_SMS = 1;
	public static final int  NOTICE_EMAIL = 2;
	public static final int NOTICE_MESSAGE = 3;
	
	/** 助通验短信通信*/
	public static final String SMS_ZT = "1";
	/** 中聚元 短信通道*/
	public static final String SMS_ZJY = "2";
	/** 梦网 短信通道*/
	public static final String SMS_MW = "3";
	
	/**
	 * 消息模板用变量
	 */
	private NoticeConstant() {

	}

}
