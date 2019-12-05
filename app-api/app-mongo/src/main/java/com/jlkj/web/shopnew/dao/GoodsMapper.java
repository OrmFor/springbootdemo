package com.jlkj.web.shopnew.dao;

import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.dto.ShopRankDto;
import com.jlkj.web.shopnew.pojo.Goods;
import cc.s2m.web.utils.webUtils.dao.BaseDao;

import java.util.List;

public interface GoodsMapper extends BaseDao<Goods, Integer> {
    List<ShopRankDto> shopRank();

}