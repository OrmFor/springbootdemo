package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ResultCode index(){
        String index = "hello,积分商城！";
        return new ResultCode(StatusCode.SUCCESS,index);
    }
}
