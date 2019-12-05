package com.jlkj.web.shopnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.dao.AddressMapper;
import com.jlkj.web.shopnew.pojo.Address;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.service.IAddress;
import com.jlkj.web.shopnew.service.IUser;
import com.jlkj.web.shopnew.yidto.request.GetAddressListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressImpl extends BaseServiceImpl<Address, AddressMapper, Integer> implements IAddress {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IUser userService;

    protected AddressMapper getDao() {
        return addressMapper;
    }

    @Override
    public List<Address> getAddressList(GetAddressListRequest getAddressListRequest) {
        User user = userService.selectByPrimaryKey(getAddressListRequest.getUid());
        Address bean = new Address();
        bean.setUid(getAddressListRequest.getUid());
        if(user != null && user.getAddressId() != 0) {
            bean.and(Expressions.notIn("id",
                    Lists.newArrayList(user.getAddressId())));
        }
        List<Address> list = this.getList(bean);

        if(list != null && list.size() > 1) {
            Address addressDefault = this.selectByPrimaryKey(user.getAddressId());
            if(addressDefault != null) {
                list.add(0, addressDefault);
            }
        }
        return list;
    }



}