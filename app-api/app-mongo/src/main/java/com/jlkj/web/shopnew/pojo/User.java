package com.jlkj.web.shopnew.pojo;

import com.jlkj.web.shopnew.pojo.entity.UserEntity;
import lombok.Data;

@Data
public class User extends UserEntity {
    private static final long serialVersionUID = 1L;

    private int userType;


}