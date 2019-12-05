package com.jlkj.web.shopnew.yidto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {

    private int uid;

    private int mgId;

    private String orderNum;

    private String result;

    private BigDecimal orderPrice;

    private BigDecimal orderIntegralPrice;
}
