package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dao.CompanyMapper;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.pojo.Staff;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyImpl extends BaseServiceImpl<Company, CompanyMapper, Integer> implements ICompany {
    @Autowired
    private CompanyMapper companyMapper;

    protected CompanyMapper getDao() {
        return companyMapper;
    }

    @Autowired
    private IStaff staffService;

    @Override
    public int getCompanyId(int uid) {
        Staff staff = new Staff();
        staff.setStaffId(uid);
        Staff bean = staffService.getByCondition(staff);

        return bean == null ? -1 : bean.getCompanyId();
    }

    @Override
    public boolean companyHasStaff(int uid, int operationId) {
        return getCompanyId(uid) == getCompanyId(operationId)  ;
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

    @Override
    public Company getCompanyByStaffId(int uid) {
        Staff staff = new Staff();
        staff.setStaffId(uid);
        Staff bean = staffService.getByCondition(staff);
        if(bean == null){
            throw new BussinessException(StatusCode.ERROR);
        }
        return this.selectByPrimaryKey(bean.getCompanyId());
    }
}