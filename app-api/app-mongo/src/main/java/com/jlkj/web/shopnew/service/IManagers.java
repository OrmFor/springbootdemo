package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.pojo.Managers;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IManagers extends BaseService<Managers, Integer> {

    int getMgIdByUid(int uid);//获取商家mg_id

    int getMgIdByStaffId(int staffId);
}