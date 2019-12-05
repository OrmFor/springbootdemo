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
public class GetOrderCountByServiceProviderRequest {

    private String addressName;

    private int mgId;

    private Date startDay;

    private Date endDay;
}
