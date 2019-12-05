package com.jlkj.web.shopnew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaticsCountDto {

    private int count;//人数

    private int staticsType;//统计类型

}
