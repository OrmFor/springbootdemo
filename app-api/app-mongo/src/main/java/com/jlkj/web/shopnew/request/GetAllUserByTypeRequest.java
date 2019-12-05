package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class GetAllUserByTypeRequest {

    private int uid;

    private  boolean isBoss;

    private int userType;

    private String dateType;

    private Date startDay;

    private Date endDay;

    private int page;

    private int pageSize;

}
