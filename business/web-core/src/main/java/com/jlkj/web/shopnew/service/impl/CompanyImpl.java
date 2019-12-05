package com.jlkj.web.shopnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import com.jlkj.web.shopnew.dao.CompanyMapper;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.service.ICompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyImpl extends BaseServiceImpl<Company, CompanyMapper, Integer> implements ICompany {
    @Autowired
    private CompanyMapper companyMapper;

    protected CompanyMapper getDao() {
        return companyMapper;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompany() throws Exception {
        Company bean = new Company();
        bean.setBossId(1);
        bean.setStatus(1);
        bean.setCompanyName("test");
        bean.setInitNum(10);
        bean.setCompanyAddress("绍兴柯桥");
        this.insert(bean);
        throw new Exception("error");
    }
}