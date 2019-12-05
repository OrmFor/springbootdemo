package com.jlkj.web.shopnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.dto.DelStaffInfoDto;
import com.jlkj.web.shopnew.dto.DistributeDto;
import com.jlkj.web.shopnew.dto.StaffAnalysisDto;
import com.jlkj.web.shopnew.dto.StaffDto;
import com.jlkj.web.shopnew.enums.EnumStaffType;
import com.jlkj.web.shopnew.pojo.*;
import com.jlkj.web.shopnew.request.*;
import com.jlkj.web.shopnew.service.*;
import com.jlkj.web.shopnew.service.mongo.ICustomerBelongMongo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class StaffController extends BaseController {

    @Autowired
    private IStaff staffService;
    @Autowired
    private ICompany companyService;

    @Autowired
    private ICompanyInvite companyInviteService;

    @Autowired
    private IUser userService;

    @Autowired
    private IManagers managersService;

    @Autowired
    private ICustomerBelongMongo customerBelongMongoService;

    private static final Logger logger = LogManager.getLogger(StaffController.class);

    //拒绝
    @RequestMapping(value = "/saveStaff" , method = RequestMethod.POST)
    @Transactional
    public ResultCode saveStaff(@RequestBody SaveStaffRequest saveStaffRequest){

        User user = userService.selectByPrimaryKey(saveStaffRequest.getUid());

        CompanyInvite condi = new CompanyInvite();
        condi.setUid(user.getId());
        condi.setFlag(0);
        condi.setBossId(saveStaffRequest.getBossId());

        CompanyInvite update = new CompanyInvite();
        update.setFlag(saveStaffRequest.getType());
        companyInviteService.updateByCondition(update,condi);
        if(saveStaffRequest.getType() == 2){//拒绝，就修改comany_invite 表flag=2 结束
            return new ResultCode(StatusCode.SUCCESS);
        }

        Staff bean = new Staff();
        bean.setStaffId(saveStaffRequest.getUid())
                .setStaffName(user.getUsername())
                .setCompanyId(saveStaffRequest.getCompanyId())
                .setStaffPic(user.getImg())
                .setStaffPosition(user.getPosition())
                .setRole(EnumStaffType.STAFF.getCode());

        int i = staffService.insert(bean);

        if(i != 1){
            return new ResultCode(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS);

    }

    //查询是否有邀请
    @RequestMapping(value = "companyInvite" , method = RequestMethod.POST)
    public ResultCode companyInvite(@RequestBody GetCompanyInviteRequest getCompanyInviteRequest){
        CompanyInvite condi = new CompanyInvite();
        condi.setUid(getCompanyInviteRequest.getUid());
        condi.setFlag(0);
        CompanyInvite bean = companyInviteService.getByCondition(condi);

        if(bean != null){
            Company company = companyService.selectByPrimaryKey(bean.getCompanyId());
            bean.setCompanyName(company == null ? "" : company.getCompanyName());

            User boss = userService.selectByPrimaryKey(bean.getBossId());

            bean.setBossName(boss == null ? "" : boss.getUsername());

        }

        return new ResultCode(StatusCode.SUCCESS,bean);
    }


    //获取我的所有员工
    @RequestMapping(value = "/getStaff" , method = RequestMethod.POST)
    public ResultCode getStaff(){
        JSONObject json = getJsonFromRequest();
        int bossId = json.getInteger("bossId");

        Company condi = new Company();
        condi.setBossId(bossId);
        Company company = companyService.getByCondition(condi);
        List<Staff> list = Lists.newArrayList();
        if(company != null) {
            Staff cond = new Staff();
            cond.setCompanyId(company.getId());
            cond.and(Expressions.in("role",Lists.newArrayList(EnumStaffType.BOSS.getCode(),
                    EnumStaffType.STAFF.getCode())));
            list = staffService.getList(cond);
        }
        return new ResultCode(StatusCode.SUCCESS,list);
    }

    //统计员工排行
    @RequestMapping(value = "/staffSort" , method = RequestMethod.POST)
    public ResultCode staffSort(@RequestBody GetStaffSortRequest getStaffSortRequest){
        List<StaffDto> list = staffService.staffSortNew(getStaffSortRequest);
        return new ResultCode(StatusCode.SUCCESS , list);
    }

    //员工分析
    @RequestMapping(value = "/staffAnalysis" , method = RequestMethod.POST)
    public ResultCode staffAnalysis(@RequestBody GetStaffAnalysisRequest getStaffAnalysisRequest){
//        List<StaffAnalysisDto> list = staffService.staffAnalysis(getStaffAnalysisRequest);
        List<StaffAnalysisDto> list = staffService.staffAnalysisNew(getStaffAnalysisRequest);
        return new ResultCode(StatusCode.SUCCESS,list);
    }


    @RequestMapping(value = "/deleteStaff" , method = RequestMethod.POST)
    @Transactional
    public ResultCode deleteStaff(@RequestBody DeleteStaffRequest deleteStaffRequest){
        boolean flag = staffService.deleteStaff(deleteStaffRequest);
        User condi = new User();
        condi.setId(deleteStaffRequest.getUid());
        User update = new User();
        update.setType(1);
        update.setPayTime(0L);
        update.setParentId(191);
        update.setSource(1);
        userService.updateByCondition(update,condi);


        try {
            Managers cond = new Managers();
            cond.setUid(deleteStaffRequest.getUid());

            Managers updateM = new Managers();
            updateM.setStatus(0);

            managersService.updateByCondition(updateM, cond);
        }catch (Exception e){

        }
        if(!flag){
            return new ResultCode(StatusCode.ERROR);
        }
        return new ResultCode(StatusCode.SUCCESS);
    }

    //员工统计视图
    @RequestMapping(value = "/staffCountView" , method = RequestMethod.POST)
    public ResultCode staffCountView(@RequestBody StaffCountRequest staffCountRequest){
        staffService.staffCountView(staffCountRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //指定员工跟进统计数
    @RequestMapping(value = "/staffCountAboutFollow" , method = RequestMethod.POST)
    public ResultCode staffCountAboutFollow(@RequestBody StaffCountRequest staffCountRequest){
        int data= staffService.getFollowNumByStaffId(staffCountRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //指定员工分享统计数
    @RequestMapping(value = "/staffCountAboutShare" , method = RequestMethod.POST)
    public ResultCode staffCountAboutShare(@RequestBody StaffCountRequest staffCountRequest){
        int data= staffService.getShareNumByStaffId(staffCountRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //指定员工订单统计数
    @RequestMapping(value = "/staffCountAboutOrders" , method = RequestMethod.POST)
    public ResultCode staffCountAboutOrders(@RequestBody StaffCountRequest staffCountRequest){
        int data= staffService.getOrdersNumByStaffId(staffCountRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //指定员工聊天统计数
    @RequestMapping(value = "/staffCountAboutChat" , method = RequestMethod.POST)
    public ResultCode staffCountAboutChat(@RequestBody StaffCountRequest staffCountRequest){
        int data= staffService.getChatNumByStaffId(staffCountRequest);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //根据员工id获取员工
    @RequestMapping(value = "/getStaffById" , method = RequestMethod.POST)
    public ResultCode getStaffById(int staffId){
        StaffDto data= staffService.getStaffById(staffId);
        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //获取删除员工信息和其它所有员工信息
    @RequestMapping(value = "/getDelStaffInfo" , method = RequestMethod.POST)
    public ResultCode getDelStaffInfo(@RequestBody DelStaffInfoRequest delStaffInfoRequest){
        Staff condi=new Staff();
        condi.setStaffId(delStaffInfoRequest.getStaffId());
        Staff staff = staffService.getByCondition(condi);

        DelStaffInfoDto delStaffInfoDto=new DelStaffInfoDto();
        delStaffInfoDto.setStaffId(staff.getStaffId());
        delStaffInfoDto.setStaffName(staff.getStaffName());
        delStaffInfoDto.setStaffPic(staff.getStaffPic());

        //统计删除员工的所有客户
        int customerCount=customerBelongMongoService.getDelStaffCustomerCount(delStaffInfoRequest);
        delStaffInfoDto.setCustomerCount(customerCount);

        //获取其它所有员工信息
        List<Staff> list = staffService.getAllStaffExceptSelf(delStaffInfoRequest.getStaffId(),staff.getCompanyId());

        Map<String,Object> data=new HashMap<>();
        data.put("list",list);
        data.put("delStaffInfoDto",delStaffInfoDto);

        return new ResultCode(StatusCode.SUCCESS,data);
    }

    //分配 并删除
    @RequestMapping(value = "/distribute" , method = RequestMethod.POST)
    @Transactional
    public ResultCode distribute(@RequestBody DistributeRequest distributeRequest){
        List<DistributeDto> list=distributeRequest.getList();
        int originalStaffId=distributeRequest.getOriginalStaffId();
        for (DistributeDto distributeDto:list){
            if(distributeDto.getCount() == 0){
                continue;
            }
            customerBelongMongoService.distributeUpdate(originalStaffId,distributeDto);
        }
        staffService.deleteOriginalStaff(originalStaffId);//第四步 删除
        return new ResultCode(StatusCode.SUCCESS);
    }
}
