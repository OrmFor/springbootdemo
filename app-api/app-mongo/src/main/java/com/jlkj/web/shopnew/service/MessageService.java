package com.jlkj.web.shopnew.service;



import com.jlkj.web.shopnew.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.util.List;

public interface MessageService {


    List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer
            rows);


    Message findMessageById(String id);


    UpdateResult updateMessageState(ObjectId id, Integer status);


    Message saveMessage(Message message);


    DeleteResult deleteMessage(String id);

}