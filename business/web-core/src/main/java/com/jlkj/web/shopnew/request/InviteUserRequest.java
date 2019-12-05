package com.jlkj.web.shopnew.request;

import lombok.Data;

@Data
public class InviteUserRequest {

    private int bossId;

    private String phone;

    private String belongAppId;
}
