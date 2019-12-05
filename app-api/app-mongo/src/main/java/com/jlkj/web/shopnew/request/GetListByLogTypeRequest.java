package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetListByLogTypeRequest {

    private int page;

    private int pageSize;

    private int logType;

    //筛选type
    private String dateType;

    private Date startDay;

    private Date endDay;
}
