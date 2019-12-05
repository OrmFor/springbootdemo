//package com.jlkj.web.shopnew.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * kafka生产方
// */
//@Component
////@EnableScheduling //开启定时任务功能
//public class KafkaProducer {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    //@Scheduled(cron = "*/5 * * * * ?")
//    public void send(){
//        String currentDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        System.out.println("=========send data============");
//        kafkaTemplate.send("topic","["+currentDate+"]:kafka data");//发送主题消息
//    }
//
//}
