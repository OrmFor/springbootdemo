package com.jlkj.web.shopnew.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFollowDto {

    private int uid;
    private int belongUid;

    private long count;

    private Date lastFollowTime;
}
