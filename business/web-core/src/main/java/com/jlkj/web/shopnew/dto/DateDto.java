package com.jlkj.web.shopnew.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateDto {
    private Date startDay;

    private Date endDay;

    private Date startAddDay;

    private Date endAddDay;
}
