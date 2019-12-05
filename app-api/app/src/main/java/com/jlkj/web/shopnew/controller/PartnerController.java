package com.jlkj.web.shopnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.StaffDto;
import com.jlkj.web.shopnew.dto.UserDto;
import com.jlkj.web.shopnew.enums.EnumFlagType;
import com.jlkj.web.shopnew.enums.EnumStaffType;
import com.jlkj.web.shopnew.exception.BussinessException;
import com.jlkj.web.shopnew.pojo.*;
import com.jlkj.web.shopnew.request.*;
import com.jlkj.web.shopnew.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class PartnerController extends BaseController {

    @Autowired
    private IStaff staffService;

    @Autowired
    private IUser userService;

    @Autowired
    private ICompany companyService;

    @Autowired
    private ICompanyInvite companyInviteService;

    @Autowired
    private IManagers managersService;

    //邀请指定手机号的用户成为合伙人(查询邀请人相关信息)
    @RequestMapping(value = "/inviteUserBePartner" , method = RequestMethod.POST)
    public ResultCode inviteUser(@RequestBody InviteUserRequest inviteUserRequest){
        User condi = new User();
        condi.setPhone(inviteUserRequest.getPhone());//设置输入的手机号
        condi.setBelongAppid(inviteUserRequest.getBelongAppId());
        User user = userService.getByCondition(condi);//查找该手机号关联的用户（合伙人也是用户的一部分）
        if(user == null){
            return new ResultCode(StatusCode.ERROR_NO_USER);
        }

        Staff condiStaff = new Staff();
        condiStaff.setStaffId(user.getId());
        condiStaff.setRole(EnumStaffType.PARTNER.getCode());
        Staff staff = staffService.getByCondition(condiStaff);//查询合伙人记录有没有

        if(staff != null && staff.getCompanyId() != 0){//有-已接受邀请成为了合伙人
            throw new BussinessException(StatusCode.ERROR_HAS_INVITE_PARTNER);
        }

        //获取boss底下所有子账号 减去1去除boss
        int count = staffService.getCountSubAccountByBossId(inviteUserRequest.getBossId()) - 1;
        Company condition = new Company();
        condition.setBossId(inviteUserRequest.getBossId());
        Company company = companyService.getByCondition(condition);
        if(count >= company.getInitNum()){
            return new ResultCode(StatusCode.ERROR_SUBACCOUNT_NUM);
        }

        UserDto dto = new UserDto();//没有-可以邀请，返回用户信息
        dto.setUid(user.getId());
        dto.setUserName(user.getUsername());
        dto.setBossId(inviteUserRequest.getBossId());//设置邀请人
        return new ResultCode(StatusCode.SUCCESS,dto);
    }

    /**
     * 确定邀请某个用户成为合伙人（不能有名片）
     * @param confirmInviteUserRequest
     * @return
     */
    @RequestMapping(value = "/confirmInviteUserBePartner" , method = RequestMethod.POST)
    public ResultCode confirmInviteUser(@RequestBody ConfirmInviteUserRequest confirmInviteUserRequest){
        User cond = new User();
        cond.setType(2);
        cond.setId(confirmInviteUserRequest.getUid());
        User user = userService.getByCondition(cond);//被邀请人是否已经购买名片
        if(user != null){
            return new ResultCode(StatusCode.ERROR_HAS_BUSSINESS_CARD);
        }

        User boss = userService.selectByPrimaryKey(confirmInviteUserRequest.getBossId());//邀请人
        Company condi = new Company();
        condi.setBossId(boss.getId());
        Company company = companyService.getByCondition(condi);//获得邀请人企业

        CompanyInvite companyInvite = new CompanyInvite();
        companyInvite.setUid(confirmInviteUserRequest.getUid());
        companyInvite.setBossId(confirmInviteUserRequest.getBossId());
        companyInvite.setCompanyId( company.getId());
        companyInvite.and(Expressions.notIn("flag", Lists.newArrayList(EnumFlagType.COMPANY_INVITE_REFUSE.getCode(),
                EnumFlagType.COMPANY_INVITE_DELETE.getCode())));//0发送邀请 1同意 2拒绝
        CompanyInvite invite = companyInviteService.getByCondition(companyInvite);//根据邀请信息查找有没有接受邀请记录
        if(invite != null){//针对某用户的邀请已发送或已同意
            return new ResultCode(StatusCode.ERROR_HAS_INVITE_PARTNER);
        }

        CompanyInvite bean = new CompanyInvite();//添加企业邀请记录
        bean.setFlag(0);//发送邀请
        bean.setUid(confirmInviteUserRequest.getUid());
        bean.setBossId(confirmInviteUserRequest.getBossId());
        bean.setBossName(boss.getUsername());
        bean.setCompanyId( company.getId());
        bean.setCompanyName(company.getCompanyName());
        bean.setRole(EnumStaffType.PARTNER.getCode());//合伙人标识
        int i = companyInviteService.insert(bean);//同意，插入

        if(i != 1) {
            return new ResultCode(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    //同意邀请 保存合伙人
    //拒绝
    @RequestMapping(value = "/savePartner" , method = RequestMethod.POST)
    @Transactional
    public ResultCode savePartner(@RequestBody SaveStaffRequest saveStaffRequest){

        User user = userService.selectByPrimaryKey(saveStaffRequest.getUid());//获得当前用户
        if(user==null){
            return new ResultCode(StatusCode.ERROR_NO_USER);
        }

        CompanyInvite condi = new CompanyInvite();//封装条件对象
        condi.setUid(user.getId());
        condi.setFlag(0);//发送邀请
        condi.setBossId(saveStaffRequest.getBossId());

        CompanyInvite update = new CompanyInvite();//封装更新对象
        update.setFlag(saveStaffRequest.getType());//1同意 2拒绝
        companyInviteService.updateByCondition(update,condi);//(更新内容,条件内容)
        if(saveStaffRequest.getType() == 2){//拒绝，就修改comany_invite 表flag=2 结束
            return new ResultCode(StatusCode.SUCCESS);
        }
        //同意 则继续
        Staff bean = new Staff();
        bean.setStaffId(saveStaffRequest.getUid())
                .setStaffName(user.getUsername())
                .setCompanyId(saveStaffRequest.getCompanyId())
                .setStaffPic(user.getImg())
                .setStaffPosition(user.getPosition())
                .setRole(EnumStaffType.PARTNER.getCode());

        int i = staffService.insert(bean);//添加合伙人

        if(i != 1){
            return new ResultCode(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS);

    }

    /**
     * 获取我的所有合伙人
     * @return
     */
    @RequestMapping(value = "/getPartner" , method = RequestMethod.POST)
    public ResultCode getPartner(){
        JSONObject json = getJsonFromRequest();
        int bossId = json.getInteger("bossId");

        Company condi = new Company();
        condi.setBossId(bossId);
        Company company = companyService.getByCondition(condi);//获得某个boosId的公司

        List<Staff> list = Lists.newArrayList();
        if(company != null) {//存在，获得该公司的员工列表
            Staff cond = new Staff();
            cond.setCompanyId(company.getId());//设置公司id
            cond.setRole(EnumStaffType.PARTNER.getCode());//合伙人
            list = staffService.getList(cond);//获得所有合伙人
        }
        return new ResultCode(StatusCode.SUCCESS,list);
    }

    //删除合伙人
    @RequestMapping(value = "/deletePartner" , method = RequestMethod.POST)
    @Transactional
    public ResultCode deletePartner(@RequestBody DeleteStaffRequest deleteStaffRequest){
        boolean flag = staffService.deletePartner(deleteStaffRequest);//删除合伙人

        User condi = new User();//条件内容
        condi.setId(deleteStaffRequest.getUid());//合伙人

        User update = new User();//更新内容
        update.setType(1);//重置普通用户
        update.setPayTime(0L);
        update.setParentId(191);//上级用户id
        update.setSource(1);//1购买 2邀请码

        userService.updateByCondition(update,condi);//(更新内容,条件内容)

        if(!flag){
            return new ResultCode(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    /**
     * 合伙人排行
     * @param getStaffSortRequest
     * @return
     */
    @RequestMapping(value = "/partnerSort" , method = RequestMethod.POST)
    public ResultCode partnerSort(@RequestBody GetStaffSortRequest getStaffSortRequest){
        List<StaffDto> list=staffService.sortPartner(getStaffSortRequest);
        return new ResultCode(StatusCode.SUCCESS,list);
    }

    /**
     * 合伙人"我"的分享列表
     * @param getStaffSortRequest
     * @return
     */
    @RequestMapping(value = "/partnerLogList" , method = RequestMethod.POST)
    public ResultCode partnerLogList(@RequestBody GetStaffSortRequest getStaffSortRequest){
        Map<String, Object> map = staffService.partnerLogList(getStaffSortRequest);
        return new ResultCode(StatusCode.SUCCESS,map);
    }

    /**
     * 合伙人"我"分享链接足迹
     * @param getStaffSortRequest
     * @return
     */
    @RequestMapping(value = "/partnerShareUrlCount" , method = RequestMethod.POST)
    public ResultCode partnerShareUrlCount(@RequestBody GetStaffSortRequest getStaffSortRequest){
        Map<String, Object> map = staffService.partnerShareUrlCount(getStaffSortRequest);
        return new ResultCode(StatusCode.SUCCESS,map);
    }
}
