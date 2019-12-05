package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.pojo.ScoreGoods;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.jlkj.web.shopnew.request.ScoreGoodsRequest;
import com.jlkj.web.shopnew.yidto.request.GetScoreGoodsRequest;

import java.util.List;

public interface IScoreGoods extends BaseService<ScoreGoods, Integer> {
    void saveGetShopList(String host);


    List<ScoreGoods> getScoreGoods(GetScoreGoodsRequest getScoreGoodsRequest);


    void saveOrModifyScoreGoods(ScoreGoodsRequest scoreGoodsRequest);

}