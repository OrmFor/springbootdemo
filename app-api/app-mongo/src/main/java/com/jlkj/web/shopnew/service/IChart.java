package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.dto.StaticsChartCountDto;
import com.jlkj.web.shopnew.request.GetChartDataRequest;

import java.util.List;

public interface IChart {

    List<StaticsChartCountDto> getChartData(GetChartDataRequest getChartDataRequest);//商城雷达 统计折线图
}
