package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class GetLogCountByLogTypeAndUidRequest {

    private int uid;

    private String dateType;

    private int[] logTypes;

    private boolean isBoss;

    private Date startDay;

    private Date endDay;

}
