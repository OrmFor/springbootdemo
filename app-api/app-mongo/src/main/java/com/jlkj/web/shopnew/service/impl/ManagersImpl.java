package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dao.ManagersMapper;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.pojo.Managers;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IManagers;
import com.jlkj.web.shopnew.service.IStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class ManagersImpl extends BaseServiceImpl<Managers, ManagersMapper, Integer> implements IManagers {
    @Autowired
    private ManagersMapper managersMapper;

    @Autowired
    private ICompany companyService;

    protected ManagersMapper getDao() {
        return managersMapper;
    }

    @Override
    public int getMgIdByUid(int uid) {//boss id
        //获取商家mg_id
        Managers cond = new Managers();
        cond.setUid(uid);

        Managers bean = this.getByCondition(cond);

        return bean == null ?  -1 : bean.getId();
    }

    @Override
    public int getMgIdByStaffId(int staffId) {
        Company company = companyService.getCompanyByStaffId(staffId);
        if(company == null){
            throw new BussinessException(StatusCode.ERROR);
        }
        Managers cond = new Managers();
        cond.setUid(company.getBossId());

        Managers bean = this.getByCondition(cond);

        return bean == null ?  -1 : bean.getId();
    }
}