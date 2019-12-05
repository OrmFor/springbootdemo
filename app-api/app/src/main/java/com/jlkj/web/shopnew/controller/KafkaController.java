package com.jlkj.web.shopnew.controller;


import com.jlkj.web.shopnew.core.ResultCode;
import com.jlkj.web.shopnew.core.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/1.0")
public class KafkaController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("/testKafka")
    public ResultCode send(){
        String currentDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("=========send data============");
        kafkaTemplate.send("topic","["+currentDate+"]:kafka data");//发送主题消息
        return new ResultCode(StatusCode.SUCCESS);

    }

}
