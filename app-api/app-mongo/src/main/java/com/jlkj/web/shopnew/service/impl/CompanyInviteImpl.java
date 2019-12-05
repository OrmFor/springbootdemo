package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.CompanyInviteMapper;
import com.jlkj.web.shopnew.pojo.CompanyInvite;
import com.jlkj.web.shopnew.service.ICompanyInvite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class CompanyInviteImpl extends BaseServiceImpl<CompanyInvite, CompanyInviteMapper, Integer> implements ICompanyInvite {
    @Autowired
    private CompanyInviteMapper companyInviteMapper;

    protected CompanyInviteMapper getDao() {
        return companyInviteMapper;
    }
}