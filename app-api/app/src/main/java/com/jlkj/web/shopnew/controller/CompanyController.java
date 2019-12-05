package com.jlkj.web.shopnew.controller;


import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.constant.Constant;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.enums.EnumAccountType;
import com.jlkj.web.shopnew.enums.EnumStaffType;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.pojo.Staff;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.request.SaveMyCompanyRequest;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IStaff;
import com.jlkj.web.shopnew.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/1.0")
public class CompanyController extends BaseController {

    @Autowired
    private ICompany companyService;

    @Autowired
    private IUser userService;

    @Autowired
    private IStaff staffService;


    /**
     * @return com.jlkj.web.shopnew.core.ResultCode
     * @Author wwy
     * @Description 获取我的企业
     **/
    @RequestMapping(value = "/getMyCompany", method = RequestMethod.POST)
    public ResultCode getMyCompany() {
        JSONObject json = getJsonFromRequest();
        int bossId = json.getInteger("bossId");
        Company condi = new Company();
        condi.setBossId(bossId);
        Company company = companyService.getByCondition(condi);
        return new ResultCode(StatusCode.SUCCESS, company);
    }


    @RequestMapping(value = "/saveMyCompany", method = RequestMethod.POST)
    @Transactional
    public ResultCode saveMyCompany(@RequestBody SaveMyCompanyRequest companyRequest) {
        Company condi = new Company();
        condi.setBossId(companyRequest.getBossId());
        int i = companyService.getCount(condi);
        if (i > 0) {
            return new ResultCode(StatusCode.ERROR_BOSS_HAS_COMPANY);
        }
        Company condi1 = new Company();
        condi1.setCompanyName(companyRequest.getCompanyName());
        int j = companyService.getCount(condi1);

        if (j > 0) {
            return new ResultCode(StatusCode.ERROR_COMPANY_NAME_EXISTS);
        }

        User user = userService.selectByPrimaryKey(companyRequest.getBossId());

        Company bean = new Company();
        bean.setCompanyName(companyRequest.getCompanyName());
        bean.setCompanyAddress(companyRequest.getCompanyAddress());
        bean.setBossId(companyRequest.getBossId());
        bean.setCompanyIcon(user == null ? "" : user.getComLogo());
        bean.setStatus(1);

        if(EnumAccountType.KINLIE.getType() == user.getAccountType()) {
            bean.setInitNum(Constant.INIT_NUM_KINLIE);
        }else if(EnumAccountType.COMPANY.getType() == user.getAccountType()){
            bean.setInitNum(Constant.INIT_NUM_COMPANY);
        }
        companyService.insert(bean);

        Staff staff = new Staff();
        staff.setStaffId(companyRequest.getBossId());
        staff.setStaffName(user.getUsername());
        staff.setStaffPosition("管理员");
        staff.setStaffPic(user.getLogo());
        staff.setCreateTime(new Date());
        staff.setUpdateTime(new Date());
        staff.setCompanyId(bean.getId());
        staff.setRole(EnumStaffType.BOSS.getCode());
        staffService.insert(staff);

        return new ResultCode(StatusCode.SUCCESS);

    }

}
