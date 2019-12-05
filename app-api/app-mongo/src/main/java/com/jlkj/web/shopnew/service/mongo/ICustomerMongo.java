package com.jlkj.web.shopnew.service.mongo;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.request.GetStaffSortRequest;

public interface ICustomerMongo {
    void saveCustomerBelong(JSONObject json);

    int getCustomerCount(int uid, DateDto dateDto);
}
