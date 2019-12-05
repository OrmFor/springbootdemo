package com.jlkj.web.shopnew.service.mongo;

import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.dto.DateDto;
import com.jlkj.web.shopnew.dto.OrdersDto;
import com.jlkj.web.shopnew.dto.StaticsCountDto;
import com.jlkj.web.shopnew.dto.UpdateFollowDto;
import com.jlkj.web.shopnew.pojo.Orders;
import com.jlkj.web.shopnew.request.*;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Map;

public interface ILogMongo {

    Map<String,Object> getMyCustomerLog(GetMyCustomerLogResquest getMyCustomerLogResquest);

    UpdateResult updateFollow(UpdateFollowDto dto);//更新mongo足迹(刷新跟进数据)

    void saveLogMongo(SaveLogRequest saveLogRequest);//保存mongo足迹

    Map<String,Object> getListByLogType(GetListByLogTypeRequest getListByLogTypeRequest);

    Map<String,Object> getLogListByUid(GetListByUidRequest getListByUidRequest);

    Map<String,Object> getMyFollowStatics(GetMyFollowStatiscRequest getMyFollowStatiscRequest);//我的跟进等统计信息


    Map<String,Object> getStatics(StatiscRequest statiscRequest );


    /**
     * @Author wwy
     * @Description 统计咨询人数
     * @Date 13:49 2019/9/19
     * @Param [statiscRequest]
     * @return java.lang.Integer
     **/
    Integer getChatCount(StatiscRequest statiscRequest,DateDto dateDto );

    Integer getUserTypeCount(StatiscRequest statiscRequest,DateDto dateDto,String timeType );


    /**
     * @Author wwy
     * @Description 统计用户  潜在客户 userType = 0
     *                      意向客户 userType = 1
     *                      客户    userType = 2
     * @Date 13:48 2019/9/19
     * @Param [statiscRequest, timeType]
     * @return java.lang.Integer
     **/
    Integer getUserTypeCountNew(StatiscRequest statiscRequest,DateDto dateDto,int userType );


    /**
     * @Author wwy
     * @Description 统计用户  潜在客户 userType = 0
     *                      意向客户 userType = 1
     *                      客户    userType = 2
     * @Date 13:48 2019/9/19
     * @Param [statiscRequest, timeType]
     * @return java.lang.Integer
     **/
    Integer getUserTypeAddCountNew(StatiscRequest statiscRequest, DateDto dateDto, int userType);

    /**
     * @Author wwy
     * @Description 统计跟进  userType = 0 (潜在客户),1 (意向客户)
     * @Date 13:48 2019/9/19
     * @Param [statiscRequest]
     * @return java.util.List<com.jlkj.web.shopnew.dto.StaticsCountDto>
     **/
    List<StaticsCountDto> getUserTypeFollowCount(StatiscRequest statiscRequest,DateDto dateDto);

    List<StaticsCountDto> getUserTypeFollowAddCount(StatiscRequest statiscRequest, DateDto dateDto);


    /**
     * @Author wwy
     * @Description 业务规则  统计咨询之后的跟进
     * @Date 13:53 2019/9/19
     * @Param [statiscRequest]
     * @return java.lang.Integer
     **/
    Integer getFollowAfterChatCount(StatiscRequest statiscRequest,DateDto dateDto);


    /**
     * @Author wwy
     * @Description 统计增加咨询人数
     * @Date 15:06 2019/9/19
     * @Param [statiscRequest]
     * @return java.lang.Integer
     **/
    Integer getChatAddCount(StatiscRequest statiscRequest ,DateDto dateDto);

    /**
     * @Author wwy
     * @Description 业务规则  统计咨询之后的跟进 新增
     * @Date 13:53 2019/9/19
     * @Param [statiscRequest]
     * @return java.lang.Integer
     **/
    Integer getFollowAfterChatAddCount(StatiscRequest statiscRequest,DateDto dateDto);



    /**
     * @Author wwy
     * @Description 统计用户  潜在客户 timeType = potentialTime
     *                      意向客户 timeType = intentionalTime
     *                      客户    timeType = customerTime
     * @Date 13:48 2019/9/19
     * @Param [statiscRequest, timeType]
     * @return java.lang.Integer
     **/
    @Deprecated
    Integer getUserTypeAddCount(StatiscRequest statiscRequest,DateDto dateDto,String timeType );


    Map<String,Object> getLogListByLogTypeAndUid(GetLogListByLogTypeAndUidRequest getLogListByLogTypeAndUidRequest);//获取指定logType下的所有用户对自己商城的足迹

    Map<String,Object> getLogCountByLogTypeAndUid(GetLogCountByLogTypeAndUidRequest getLogCountByLogTypeAndUidRequest);

    // 统计不同行为的记录数
    Map<String, Object> getLogListCountByLogType(GetLogCountByLogTypeAndUidRequest getLogCountByLogTypeAndUidRequest);

    Map<String,Object> getLogListByCustomer(GetLogListByCustomerRequest getLogListByCustomerRequest);

    Map<String,Object> getLogListByCustomerNew(GetLogListByCustomerRequest getLogListByCustomerRequest);

    //获取客户足迹 新
    Map<String,Object> getMyCustomerLogNew(GetMyCustomerLogResquest getMyCustomerLogResquest);

    //获取指定logType下的所有用户足迹 新
    Map<String,Object> getListByLogTypeNew(GetListByLogTypeRequest getListByLogTypeRequest);

    //获取指定用户下的所有足迹 新
    Map<String,Object> getLogListByUidNew(GetListByUidRequest getListByUidRequest);

    //获取指定员工相关的所有跟进记录
    Map<String,Object> getFollowListByLogType(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);

    //获取指定员工相关的分享记录
    Map<String,Object> getShareListByLogTypeAndStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);

    //获取指定员工相关的聊天记录
    Map<String,Object> getChartListByLogTypeAndStaffId(GetLogListByLogTypeAndStaffId getLogListByLogTypeAndStaffId);

    //统计各员工咨询数
    int getChatCount(int staffId,DateDto dateDto );

    //统计各员工分享数
    int  getShareCount(int staffId,DateDto dateDto);

    //boss总览:统计所有 新
    ResultCode getStaticsNew(StatiscRequest statiscRequest );
}
