//package com.jlkj.web.shopnew.kafka;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
///**
// * kafka消费方
// */
//@Component
//public class KafkaCustomer {
//
//    @KafkaListener(topics = {"topic"})//监听指定主题：接收发送过来的主题信息
//    public void receive(String message){
//        System.out.println("test========test");
//        System.out.println(message);
//    }
//
////    @KafkaListener(topics = {"topic"})
////    public void receive(String message){
////        System.out.println("topic========topic");
////        System.out.println(message);
////    }
//
//}
