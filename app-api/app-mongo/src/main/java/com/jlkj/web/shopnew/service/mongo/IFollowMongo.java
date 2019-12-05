package com.jlkj.web.shopnew.service.mongo;

import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.request.FollowMongoRequest;
import com.jlkj.web.shopnew.request.GetFollowListRequest;
import com.jlkj.web.shopnew.request.GetStaffSortRequest;

import java.util.Map;

public interface IFollowMongo {

    void saveFollow(FollowMongoRequest followMongoRequest);//保存跟进数据

    Map<String,Object> getFollowList(GetFollowListRequest getFollowListRequest);//获取客户相关跟进记录列表

    int getFollowCount(int  uid, DateDto dateDto);//获取归属用户相关的客户跟进总数

}
