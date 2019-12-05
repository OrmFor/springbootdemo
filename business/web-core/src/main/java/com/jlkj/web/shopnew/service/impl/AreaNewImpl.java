package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.AreaNewMapper;
import com.jlkj.web.shopnew.pojo.AreaNew;
import com.jlkj.web.shopnew.service.IAreaNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AreaNewImpl extends BaseServiceImpl<AreaNew, AreaNewMapper, Integer> implements IAreaNew {
    @Autowired
    private AreaNewMapper areaNewMapper;

    protected AreaNewMapper getDao() {
        return areaNewMapper;
    }

    @Override
    public int selectByName(String name) {
        AreaNew condi = new AreaNew();
        condi.setAreaName(name);


        AreaNew bean = this.getByCondition(condi);

        return bean == null ? -1 : bean.getYqsAreaId();
    }
}