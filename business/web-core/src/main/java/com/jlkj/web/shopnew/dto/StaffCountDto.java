package com.jlkj.web.shopnew.dto;

import lombok.Data;

@Data
public class StaffCountDto {

    private String staffName;//员工名称

    private int followNum;//跟进数

    private int shareNum;//分享次数

    private int ordersNum;//订单数

    private int chatNum;//聊天数

    private int staffId;//员工id
}
