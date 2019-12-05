package com.jlkj.web.shopnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.UserDto;
import com.jlkj.web.shopnew.enums.EnumFlagType;
import com.jlkj.web.shopnew.enums.EnumStaffType;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.Company;
import com.jlkj.web.shopnew.pojo.CompanyInvite;
import com.jlkj.web.shopnew.pojo.Staff;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.request.ConfirmInviteUserRequest;
import com.jlkj.web.shopnew.request.GetUserInfoRequest;
import com.jlkj.web.shopnew.request.InviteUserRequest;
import com.jlkj.web.shopnew.request.ModifyCustomerRequest;
import com.jlkj.web.shopnew.service.*;
import com.jlkj.web.shopnew.service.mongo.ICustomerBelongMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class UserController extends BaseController {

    @Autowired
    private IUser userService;

    @Autowired
    private IStaff staffService;

    @Autowired
    private ICompanyInvite companyInviteService;

    @Autowired
    private ICompany companyService;

    @Autowired
    private IOrders ordersService;

    @Autowired
    private ICustomerBelongMongo customerBelongMongo;



    @RequestMapping(value = "/inviteUser" , method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResultCode inviteUser(@RequestBody InviteUserRequest inviteUserRequest){
        User condi = new User();
        condi.setPhone(inviteUserRequest.getPhone());
        condi.setBelongAppid(inviteUserRequest.getBelongAppId());
        User user = userService.getByCondition(condi);

        if(user == null){
            return new ResultCode(StatusCode.ERROR_NO_USER);
        }

        Staff condiStaff = new Staff();
        condiStaff.setStaffId(user.getId());
        Staff staff = staffService.getByCondition(condiStaff);

        if(staff != null && staff.getCompanyId() != 0){
            return new ResultCode(StatusCode.ERROR_HAS_INVITE);
        }

        //获取boss底下所有子账号 减去1为去除boss
        int count = staffService.getCountSubAccountByBossId(inviteUserRequest.getBossId()) - 1 ;
        Company condition = new Company();
        condition.setBossId(inviteUserRequest.getBossId());
        Company company = companyService.getByCondition(condition);
        if(count >= company.getInitNum()){
            return new ResultCode(StatusCode.ERROR_SUBACCOUNT_NUM);
        }

        UserDto dto = new UserDto();
        dto.setUid(user.getId());
        dto.setUserName(user.getUsername());
        dto.setBossId(inviteUserRequest.getBossId());
        return new ResultCode(StatusCode.SUCCESS,dto);
    }


    /**
    * @Author wwy
    * @Description boss向员工发送邀请
    * @Date 10:24 2019/9/24
    * @Param [confirmInviteUserRequest]
    * @return com.jlkj.web.shopnew.core.ResultCode
    **/
    @RequestMapping(value = "/confirmInviteUser" , method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResultCode confirmInviteUser(@RequestBody ConfirmInviteUserRequest confirmInviteUserRequest){
        User cond = new User();
        cond.setType(2);
        cond.setId(confirmInviteUserRequest.getUid());
        User user = userService.getByCondition(cond);
        if(user != null){
            return new ResultCode(StatusCode.ERROR_HAS_BUSSINESS_CARD);
        }

        User boss = userService.selectByPrimaryKey(confirmInviteUserRequest.getBossId());
        Company condi = new Company();
        condi.setBossId(boss.getId());
        Company company = companyService.getByCondition(condi);

        CompanyInvite companyInvite = new CompanyInvite();
        companyInvite.setUid(confirmInviteUserRequest.getUid());
        companyInvite.setBossId(confirmInviteUserRequest.getBossId());
        companyInvite.setCompanyId( company.getId());
        companyInvite.and(Expressions.notIn("flag", Lists.newArrayList(EnumFlagType.COMPANY_INVITE_REFUSE.getCode(),
                EnumFlagType.COMPANY_INVITE_DELETE.getCode())));
        CompanyInvite invite = companyInviteService.getByCondition(companyInvite);
        if(invite != null){
            return new ResultCode(StatusCode.ERROR_HAS_INVITE);
        }
        CompanyInvite bean = new CompanyInvite();
        bean.setFlag(0);
        bean.setUid(confirmInviteUserRequest.getUid());
        bean.setBossId(confirmInviteUserRequest.getBossId());
        bean.setBossName(boss.getUsername());
        bean.setCompanyId( company.getId());
        bean.setCompanyName(company.getCompanyName());
        bean.setRole(EnumStaffType.STAFF.getCode());
        int i = companyInviteService.insert(bean);

        if(i != 1) {
            return new ResultCode(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

}
