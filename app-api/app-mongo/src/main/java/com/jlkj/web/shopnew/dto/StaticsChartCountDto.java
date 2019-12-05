package com.jlkj.web.shopnew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaticsChartCountDto {
    private int count;//人数

    private String date;//统计类型
}
