package com.jlkj.web.shopnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import com.jlkj.web.shopnew.dao.GoodsMapper;
import com.jlkj.web.shopnew.dto.ShopRankDto;
import com.jlkj.web.shopnew.pojo.Goods;
import com.jlkj.web.shopnew.service.IGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsImpl extends BaseServiceImpl<Goods, GoodsMapper, Integer> implements IGoods {
    @Autowired
    private GoodsMapper goodsMapper;

    protected GoodsMapper getDao() {
        return goodsMapper;
    }

    @Override
    public List<ShopRankDto> shopRank() {
        List<ShopRankDto>  result = goodsMapper.shopRank();

        return result;
    }
}