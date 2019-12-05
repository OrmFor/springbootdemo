package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.dto.StaffAnalysisDto;
import com.jlkj.web.shopnew.dto.StaffCountDto;
import com.jlkj.web.shopnew.dto.StaffDto;
import com.jlkj.web.shopnew.pojo.Staff;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.jlkj.web.shopnew.request.*;

import java.util.List;
import java.util.Map;

public interface IStaff extends BaseService<Staff, Integer> {

    List<Integer> getCompanyStaff(int uid);//返回自己或企业所有员工

    List<Integer> getCompanyStaffNoPartner(int uid);

    List<StaffDto> staffSort(GetStaffSortRequest getStaffSortRequest);

    List<StaffAnalysisDto> staffAnalysis(GetStaffAnalysisRequest getStaffAnalysisRequest);//员工分析

    boolean deleteStaff(DeleteStaffRequest deleteStaffRequest);

    List<Integer> getStaffUids(int uid);

    List<Integer> getStaffUidsNoPartner(int uid);

    boolean deletePartner(DeleteStaffRequest deleteStaffRequest);//删除合伙人

    List<StaffDto> sortPartner(GetStaffSortRequest getStaffSortRequest);//合伙人排行

    Map<String,Object> partnerLogList(GetStaffSortRequest getStaffSortRequest);//我的合伙分享列表

    int getRole(int operationId);

    List<StaffCountDto> staffCountView(StaffCountRequest staffCountRequest);//员工统计视图

    int getFollowNumByStaffId(StaffCountRequest staffCountRequest);

    int getShareNumByStaffId(StaffCountRequest staffCountRequest);

    int getOrdersNumByStaffId(StaffCountRequest staffCountRequest);

    int getChatNumByStaffId(StaffCountRequest staffCountRequest);

    StaffDto getStaffById(int staffId);//根据员工id获取员工信息

    List<StaffDto> staffSortNew(GetStaffSortRequest getStaffSortRequest);//员工排行 新

    List<StaffAnalysisDto> staffAnalysisNew(GetStaffAnalysisRequest getStaffAnalysisRequest);//员工分析 新

    Map<String,Object> partnerShareUrlCount(GetStaffSortRequest getStaffSortRequest);//合伙人"我"分享链接足迹

    int getCountSubAccountByBossId(int bossId);

    List<Staff> getAllStaffExceptSelf(int staffId,int companyId);//获取其它所有员工

    void deleteOriginalStaff(int originalStaffId);//删除之前的员工

    Staff getStaffByStaffId(int staffId);
}