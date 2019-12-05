package com.jlkj.web.shopnew.dao;

import com.jlkj.web.shopnew.pojo.Staff;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffMapper extends BaseDao<Staff, Integer> {

    List<Staff> getAllStaffExceptSelf(@Param("staffId")int staffId,@Param("companyId")int companyId);//获得其它所有员工

}