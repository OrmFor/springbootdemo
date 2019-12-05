package com.jlkj.web.shopnew.enums;


import lombok.Getter;

@Getter
public enum EnumLogType {
    // 人脉：
        // 点赞
        // 收藏
        // 保存海报
        // 分享
        // 浏览
    //商城：
        // 浏览
        // 加购物车
        // 下单
        // 分享
        // 保存图片
    //其他：
    // 打开聊天窗口
    // 访问官网
    // 播放视频
    // 播放语音
    // 点击购买名片
    // 购买名片
    // 购买名片成功
    // 登记收货地址
    //18 提交商品订单数 19.商品付款成功 20 取消订单 21 动态点赞',

    LOG_THUMBS_UP(1, "人脉点赞"),
    LOG_STORE(2, "人脉收藏"),
    LOG_SHARE(30, "人脉保存海报"),
    LOG_SHOP(3, "人脉分享"),
    LOG_SHOP_SEE(5, "人脉浏览"),
    LOG_CHAT(6, "聊天咨询(窗口)"),
    LOG_SUMBIT_ORDER(31, "商城加购物车"),
    LOG_BUY(18, "商城下单"),
    LOG_SHOP_SHARE(32, "商城分享"),
    LOG_ADD_SHOPPING_CART(33, "商城保存图片"),

    LOG_SHOP_BROWSE(13,"商城浏览"),
    LOG_OTHER_VISIT(7,"访问官网"),
    LOG_OTHER_PLAY_VIDEO(9,"播放视频"),
    LOG_OTHER_PLAY_VOICE(10,"播放语音"),
    LOG_OTHER_CLICK_BUY_CARD(14,"点击购买名片"),
    LOG_OTHER_BUY_CARD(34,"购买名片"),
    LOG_OTHER_BUY_CARD_SUCCESS(35,"购买名片成功"),
    LOG_OTHER_REGISTER_RECEIVE_ADDRESS(17,"登记收货地址"),
    LOG_OTHER_COMMIT_ORDERS_COUNT(18,"提交商品订单数"),
    LOG_OTHER_GOODS_PAY_SUCCESS(19,"商品付款成功"),
    LOG_OTHER_ORDERS_CANCEL(20,"取消订单"),
    LOG_OTHER_DYNAMIC_PRAISE(21,"动态点赞"),
    ;


    private int type;

    private String typeName;

    EnumLogType(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }


}
