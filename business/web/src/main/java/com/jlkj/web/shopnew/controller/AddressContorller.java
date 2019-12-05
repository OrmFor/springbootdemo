package com.jlkj.web.shopnew.controller;

import com.jlkj.web.shopnew.common.HttpUrlConfig;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.yidto.request.EditYqsAddressRequest;
import com.jlkj.web.shopnew.service.IAddress;
import com.jlkj.web.shopnew.service.YiService;
import com.jlkj.web.shopnew.yidto.request.GetAddressDetailRequest;
import com.jlkj.web.shopnew.yidto.request.GetAddressListRequest;
import com.jlkj.web.shopnew.yidto.request.SaveAddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/1.0")
public class AddressContorller extends BaseController {

    @Autowired
    private IAddress addressService;

    @Autowired
    private YiService yiService;

    @Autowired
    private HttpUrlConfig httpUrlConfig;


    /**
    * @Author wwy
    * @Description 保存地址接口
    * @Date 18:31 2019/11/8
    * @Param [saveAddressRequest]
    * @return com.jlkj.web.shopnew.core.ResultCode
    **/
    @RequestMapping("/saveAddress")
    public ResultCode saveAddress(@RequestBody SaveAddressRequest saveAddressRequest){
        saveAddressRequest.setHost(httpUrlConfig.getHost());
        saveAddressRequest.setUserId(httpUrlConfig.getUserId());
        yiService.saveAddress(saveAddressRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }

    /**
    * @Author wwy
    * @Description 获取地址列表
    * @Date 17:35 2019/11/25
    * @Param [getAddressListRequest]
    * @return com.jlkj.web.shopnew.core.ResultCode
    **/
    @RequestMapping("/getAddressList")
    public ResultCode getAddressList(@RequestBody GetAddressListRequest getAddressListRequest){
        List<Address> list = addressService.getAddressList(getAddressListRequest);
        return new ResultCode(StatusCode.SUCCESS,list);
    }

    /**
    * @Author wwy
    * @Description 修改地址
    * @Date 15:02 2019/11/25
    * @Param [editYqsAddressRequest]
    * @return com.jlkj.web.shopnew.core.ResultCode
    **/
    @RequestMapping("/editAddress")
    public ResultCode editYqsAddress(@RequestBody EditYqsAddressRequest editYqsAddressRequest){
        editYqsAddressRequest.setHost(httpUrlConfig.getHost());
        editYqsAddressRequest.setUserId(httpUrlConfig.getUserId());
        yiService.editYqsAddress(editYqsAddressRequest);
        return new ResultCode(StatusCode.SUCCESS);
    }

    /**
    * @Author wwy
    * @Description 获取地址详情
    * @Date 17:56 2019/11/28
    * @Param [addressId]
    * @return com.jlkj.web.shopnew.core.ResultCode
    **/
    @RequestMapping("/getAddressDetail")
    public ResultCode getAddressDetail(@RequestBody GetAddressDetailRequest getAddressDetailRequest){
        Address condi = new Address();
        condi.setAddressId(getAddressDetailRequest.getAddressId());
        Address bean = addressService.getByCondition(condi);
        return new ResultCode(StatusCode.SUCCESS,bean);
    }


}
