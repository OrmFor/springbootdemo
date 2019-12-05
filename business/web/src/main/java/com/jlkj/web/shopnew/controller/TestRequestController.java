package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.service.ICompany;
import com.jlkj.web.shopnew.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestRequestController extends BaseController {

    @Autowired
    private IUser userService;

    @Autowired
    private ICompany companyService;

	@RequestMapping("/getUser")
    public ResultCode getUser(){
        User user = userService.selectByPrimaryKey(3140);
        return new ResultCode(StatusCode.SUCCESS,user);
    }


    @RequestMapping("/saveCompany")
    public ResultCode  saveCompany() throws Exception {
        companyService.saveCompany();
        return new ResultCode(StatusCode.SUCCESS);
    }

}
