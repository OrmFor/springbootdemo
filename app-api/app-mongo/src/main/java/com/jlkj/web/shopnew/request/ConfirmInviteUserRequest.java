package com.jlkj.web.shopnew.request;


import lombok.Data;

@Data
public class ConfirmInviteUserRequest {
    private int bossId;//邀请人

    private int uid;//被邀请人
}
