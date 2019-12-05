package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.SysTaskHandelMapper;
import com.jlkj.web.shopnew.pojo.SysTaskHandel;
import com.jlkj.web.shopnew.service.ISysTaskHandel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SysTaskHandelImpl extends BaseServiceImpl<SysTaskHandel, SysTaskHandelMapper, Integer> implements ISysTaskHandel {
    @Autowired
    private SysTaskHandelMapper sysTaskHandelMapper;

    protected SysTaskHandelMapper getDao() {
        return sysTaskHandelMapper;
    }
}