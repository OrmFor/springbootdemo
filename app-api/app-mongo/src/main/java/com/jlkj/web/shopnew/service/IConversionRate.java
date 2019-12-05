package com.jlkj.web.shopnew.service;

import com.jlkj.web.shopnew.dto.ConversionRateDto;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.request.GetConversionRateRequest;
import com.jlkj.web.shopnew.request.GetStaffSortRequest;
import com.jlkj.web.shopnew.request.StatiscRequest;

public interface IConversionRate {
    ConversionRateDto getConversionRate(StatiscRequest statiscRequest);//获得转换率

    Integer getAllOfCustomer(GetConversionRateRequest getConversionRateRequest,
                             String timeType);

    int getCustomerByType(GetStaffSortRequest getStaffSortRequest, DateDto dateDto, String timeType);

    int getCustomerByType(GetStaffSortRequest getStaffSortRequest, DateDto dateDto, int userType);//按客户类型统计

}
