package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.pojo.Company;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ICompany extends BaseService<Company, Integer> {

    public int getCompanyId(int uid);

    boolean companyHasStaff(int uid, int operationId);

    void saveCompany() throws Exception;

    Company getCompanyByStaffId(int uid);

}