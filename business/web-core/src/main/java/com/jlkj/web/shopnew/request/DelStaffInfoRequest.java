package com.jlkj.web.shopnew.request;

import lombok.Data;

@Data
public class DelStaffInfoRequest {

    private int staffId;//员工id

    private String staffName;//员工名称

    private String staffPic;//员工头像

}
