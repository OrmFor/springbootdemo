package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class GetMyFollowStatiscRequest {

    private int uid;//登录ID

    private String dateType;

    private Date startDay;

    private Date endDay;

}
