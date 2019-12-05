package com.jlkj.web.shopnew.dao;

import com.jlkj.web.shopnew.dto.LogDto;
import com.jlkj.web.shopnew.pojo.Log;
import cc.s2m.web.utils.webUtils.dao.BaseDao;

import java.util.List;

public interface LogMapper extends BaseDao<Log, Integer> {
    List<LogDto> getInsertMongoDbAll();
}