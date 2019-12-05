package com.test.request;

import com.test.chat.Message;
import com.test.chat.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestRequestController {
    @Autowired
   private MessageDAO messageDAO;


    @RequestMapping("/test")
    public String Test(){
        List<Message> list = this.messageDAO.findListByFromAndTo(1001L, 1002L, 1,
                1);
        for (Message message : list) {
            System.out.println(message);
        }
        return "success";
    }



}
