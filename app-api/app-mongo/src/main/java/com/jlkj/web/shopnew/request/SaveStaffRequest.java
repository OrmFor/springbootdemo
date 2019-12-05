package com.jlkj.web.shopnew.request;


import lombok.Data;

@Data
public class SaveStaffRequest {

    private int uid;//被邀请方

    private int bossId;//邀请方

    private int companyId;//企业id

    private String companyName;//企业名称

    private int type;//1 确认接受 2 拒绝

}
