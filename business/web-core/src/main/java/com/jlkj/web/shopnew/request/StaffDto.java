package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class StaffDto {
    private int staffId;

    private String staffName;

    private String staffPosition;

    private String staffPic;

    private int count;// 计数

    private Date createTime;// 加入时间
}
