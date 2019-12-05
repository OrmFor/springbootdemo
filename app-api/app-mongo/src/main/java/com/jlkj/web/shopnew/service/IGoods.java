package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.dto.ShopRankDto;
import com.jlkj.web.shopnew.pojo.Goods;
import cc.s2m.web.utils.webUtils.service.BaseService;

import java.util.List;

public interface IGoods extends BaseService<Goods, Integer> {
    List<ShopRankDto> shopRank();

}