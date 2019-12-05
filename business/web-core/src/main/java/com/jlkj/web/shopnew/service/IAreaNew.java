package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.pojo.AreaNew;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IAreaNew extends BaseService<AreaNew, Integer> {
    int selectByName(String name);


}