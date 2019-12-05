package com.jlkj.web.shopnew.service.mongo;

import com.jlkj.web.shopnew.dto.DistributeDto;
import com.jlkj.web.shopnew.pojo.mongo.CustomerBelongMongo;
import com.jlkj.web.shopnew.request.*;

import java.util.Map;

public interface ICustomerBelongMongo {
    void saveCustomerBelong(CustomerBelongMongo bean);//保存客户记录

    void modifyCustomer(ModifyCustomerRequest bean);//修改客户类型

    void modifyCustomerLabel(ModifyCustomerLabel bean);//修改客户标签

    int getUserType(GetUserInfoRequest getUserInfoRequest);//获取归属用户的某客户的类型

    String getUserLabel(GetUserInfoRequest getUserInfoRequest);//获得客户标签

    Map<String,Object> getAllUserByType(GetAllUserByTypeRequest getAllUserByTypeRequest);//获取某类型的客户数据

    Map<String,Object> getAllUserByTypeNew(GetAllUserByTypeRequest getAllUserByTypeRequest);//获取某类型的客户各行为数据 聚合 新

    int getDelStaffCustomerCount(DelStaffInfoRequest delStaffInfoRequest);//获取指定员工的客户记录数量

    void distributeUpdate(int originalStaffId, DistributeDto distributeDto);//分配更新customerBelongMongo followMongo logMongo
}
