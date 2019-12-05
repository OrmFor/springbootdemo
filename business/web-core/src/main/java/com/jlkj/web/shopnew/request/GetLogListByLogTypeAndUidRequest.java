package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetLogListByLogTypeAndUidRequest {


    private int page;

    private int pageSize;

    private int logType;//足迹类型 浏览，加入购物车，下单，分享


    private String dateType;//选择日期类型

    private Date startDay;

    private Date endDay;

    private int uid;//员工id或老板id

    private boolean isBoss;//boss表示
}
