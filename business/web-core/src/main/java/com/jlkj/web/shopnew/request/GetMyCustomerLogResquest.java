package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetMyCustomerLogResquest {
    private int operationId;

    private int page;

    private int pageSize;

    private int userType;//0潜在客户,1意向客户,2客户,3所有

    private String dateType;

    private Date startDay;

    private Date endDay;

}
