package com.jlkj.web.shopnew.service.impl;

import com.jlkj.web.shopnew.dao.UserMapper;
import com.jlkj.web.shopnew.pojo.User;
import com.jlkj.web.shopnew.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class UserImpl extends BaseServiceImpl<User, UserMapper, Integer> implements IUser {
    @Autowired
    private UserMapper userMapper;

    protected UserMapper getDao() {
        return userMapper;
    }

    @Override
    public void saveUser() {


        User user = new User();
        user.setBelongAppid("1");
        user.setSource(1);
        user.setOpenid("123123");
        user.setWxOpenid("asdfasfdasdf");

        this.insert(user);
    }
}