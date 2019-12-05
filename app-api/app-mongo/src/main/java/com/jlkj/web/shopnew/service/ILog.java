package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.dto.LogDto;
import com.jlkj.web.shopnew.pojo.Log;
import cc.s2m.web.utils.webUtils.service.BaseService;

import java.util.List;

public interface ILog extends BaseService<Log, Integer> {
    List<LogDto> getInsertMongoDbAll();
}