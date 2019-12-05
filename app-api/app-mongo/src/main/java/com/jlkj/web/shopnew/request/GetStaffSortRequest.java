package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class GetStaffSortRequest {
    private int uid;//boss，合伙人...

    private String dateType;//选择日期类型

    private int countType;// 统计类型

    private Date startDay;// 开始日子

    private Date endDay;// 结束日子

    private int page;

    private int pageSize;

}
