package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.pojo.Address;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.jlkj.web.shopnew.yidto.request.EditYqsAddressRequest;
import com.jlkj.web.shopnew.yidto.request.GetAddressListRequest;

import java.util.List;

public interface IAddress extends BaseService<Address, Integer> {
    List<Address> getAddressList(GetAddressListRequest getAddressListRequest);
}