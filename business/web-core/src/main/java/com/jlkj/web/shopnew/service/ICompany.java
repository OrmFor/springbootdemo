package com.jlkj.web.shopnew.service;

import cc.s2m.web.utils.webUtils.service.BaseService;
import com.jlkj.web.shopnew.pojo.Company;

public interface ICompany extends BaseService<Company, Integer> {

    void saveCompany() throws Exception;

}