package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.SysTaskLogMapper;
import com.jlkj.web.shopnew.pojo.SysTaskLog;
import com.jlkj.web.shopnew.service.ISysTaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class SysTaskLogImpl extends BaseServiceImpl<SysTaskLog, SysTaskLogMapper, Integer> implements ISysTaskLog {
    @Autowired
    private SysTaskLogMapper sysTaskLogMapper;

    protected SysTaskLogMapper getDao() {
        return sysTaskLogMapper;
    }
}