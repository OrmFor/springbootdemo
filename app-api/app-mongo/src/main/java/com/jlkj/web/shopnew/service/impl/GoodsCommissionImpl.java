package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.GoodsCommissionMapper;
import com.jlkj.web.shopnew.pojo.GoodsCommission;
import com.jlkj.web.shopnew.service.IGoodsCommission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class GoodsCommissionImpl extends BaseServiceImpl<GoodsCommission, GoodsCommissionMapper, Integer> implements IGoodsCommission {
    @Autowired
    private GoodsCommissionMapper goodsCommissionMapper;

    protected GoodsCommissionMapper getDao() {
        return goodsCommissionMapper;
    }
}