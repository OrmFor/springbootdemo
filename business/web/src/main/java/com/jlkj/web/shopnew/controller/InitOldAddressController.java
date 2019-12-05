package com.jlkj.web.shopnew.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.common.HttpUrlConfig;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.service.IAddress;
import com.jlkj.web.shopnew.service.IUser;
import com.jlkj.web.shopnew.service.YiService;
import com.jlkj.web.shopnew.yidto.request.SaveAddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class InitOldAddressController extends BaseController {

    @Autowired
    private IAddress addressService;

    @Autowired
    private IUser userService;

    @Autowired
    private YiService yiService;



    @RequestMapping("/initOldAddress")
    @Deprecated
    public ResultCode initOldAddress(){
        Address condi = new Address();
        condi.and(Expressions.in("address_id",Lists.newArrayList(-1)));
        List<Address> list = addressService.getList(condi);
        if(list != null && list.size() > 0){
            for(Address address : list){
                User user = userService.selectByPrimaryKey(address.getUid());
                SaveAddressRequest request = new SaveAddressRequest();
                request.setAreaId(address.getAreaId());
                request.setAreaInfo(address.getAddress());
                request.setMobile(user.getPhone());
                request.setTrueName(address.getConsignee());
                request.setUid(address.getUid());
                request.setZip(000000);

                yiService.saveAddress(request);

            }

        }
        return new ResultCode(StatusCode.SUCCESS);
    }
}
