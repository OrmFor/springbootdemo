package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScoreGoodsRequest {
    private int id;
    private int goodsId;
    private String goodsName;
    private String qrImgPath;
    private BigDecimal score;
    private BigDecimal costPrice;
    private BigDecimal goodsCurrentPrice;
}
