package com.jlkj.web.shopnew.service.mongo;



import com.jlkj.web.shopnew.pojo.mongo.BrowseRecord;
import com.jlkj.web.shopnew.request.UpdateCustomerRequest;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.Map;

public interface IBrowseRecord {


    Map<String, Object> findList(String visited, Integer page, int rows);


    BrowseRecord findBrowseRecordByVisitor(String visitor);

    UpdateResult updateCustomer(UpdateCustomerRequest updateCustomerRequest);

    UpdateResult update(String visitor);


    void saveBrowseRecord(BrowseRecord browseRecord);


    DeleteResult delete(String id);

    Map<String, Object> count(String v , String v1);
}
