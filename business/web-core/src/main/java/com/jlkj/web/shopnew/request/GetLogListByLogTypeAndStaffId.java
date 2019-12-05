package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetLogListByLogTypeAndStaffId {

    private int page;

    private int pageSize;

    private int staffId;//员工

    private int bossId;//被访问者

    private String dateType;//选择日期类型 今日 昨日 本周 筛选

    private Date startDay;

    private Date endDay;

    private int logType;//足迹行为 跟进 分享 订单  聊天

    private List<Integer> staffIds;//

    private List<Integer> statuss;//

    private int mgId;//boss的商户id
}
