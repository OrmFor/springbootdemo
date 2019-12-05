package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class StaffCountRequest {

    private int uid;//用户id(boss)

    private String dateType;//选择日期类型

    private Date startDay;// 开始日子

    private Date endDay;// 结束日子

    private int page;

    private int pageSize;

    private int staffId;//员工id

}
