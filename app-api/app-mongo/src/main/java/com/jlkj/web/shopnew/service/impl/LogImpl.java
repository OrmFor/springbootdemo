package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.LogMapper;
import com.jlkj.web.shopnew.dto.LogDto;
import com.jlkj.web.shopnew.pojo.Log;
import com.jlkj.web.shopnew.service.ILog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

import java.util.List;

@Service
public class LogImpl extends BaseServiceImpl<Log, LogMapper, Integer> implements ILog {
    @Autowired
    private LogMapper logMapper;

    protected LogMapper getDao() {
        return logMapper;
    }

    @Override
    public List<LogDto> getInsertMongoDbAll() {
        return logMapper.getInsertMongoDbAll();
    }
}