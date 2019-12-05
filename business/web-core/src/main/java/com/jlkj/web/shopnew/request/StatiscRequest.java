package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class StatiscRequest {

    private int uid;//登录ID  GetStaffAnalysisRequest 老板id  StatiscRequest 员工id/老板id

    private String dateType;//选择日期类型

    private boolean isBoss;//标识是否为老板

    private Date startDay;

    private Date endDay;

    private int mgId;//boss的商户id

}
