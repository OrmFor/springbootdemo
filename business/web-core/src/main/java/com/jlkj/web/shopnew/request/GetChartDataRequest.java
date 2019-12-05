package com.jlkj.web.shopnew.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetChartDataRequest {
    private String dateType;//1 今日 2本周 3.本月 4.近三月

    private Date startDay;

    private Date endDay;

    private int operationId;//被操作人id

    private int logType;//客户足迹行为 浏览，加入购物车，下单，分享


}
