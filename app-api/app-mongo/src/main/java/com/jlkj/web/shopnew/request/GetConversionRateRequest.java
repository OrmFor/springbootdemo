package com.jlkj.web.shopnew.request;


import lombok.Data;

import java.util.Date;

@Data
public class GetConversionRateRequest {

    boolean isBoss;

    int uid;

    String dateType;

    Date startDay;

    Date endDay;


}
