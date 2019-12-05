package com.jlkj.web.shopnew.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetOrderListByUserTypeRequest {

    private int uid;

    private String dateType;

    private boolean isBoss;

    private Date startDay;

    private Date endDay;

    private String addressName;

    private int mgId;

}
