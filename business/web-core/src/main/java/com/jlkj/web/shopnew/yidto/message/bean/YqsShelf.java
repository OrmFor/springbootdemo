package com.jlkj.web.shopnew.yidto.message.bean;

/**
 * 上下架消息封装
 */
public class YqsShelf {
    private int goods_id;//上下架的商品id（易企顺的商品id）
    private int status;//0下架   1上架

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
