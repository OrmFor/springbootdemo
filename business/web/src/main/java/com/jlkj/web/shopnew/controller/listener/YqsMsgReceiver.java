package com.jlkj.web.shopnew.controller.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jlkj.web.shopnew.common.HttpUrlConfig;
import com.jlkj.web.shopnew.common.rabbit.YqsRabbitConfig;
import com.jlkj.web.shopnew.service.notify.StrategyContext;
import com.jlkj.web.shopnew.yidto.message.bean.MqMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;


@Component
@RabbitListener(queues = YqsRabbitConfig.QUEUE_SMALL_PROGRAM)
@Profile("prod")
public class YqsMsgReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StrategyContext strategyContext;

    @Autowired
    private HttpUrlConfig httpUrlConfig;

    public static final List<String> msgTypeList = Lists.newArrayList(
            MqMessage.MSG_GOODS_OFF,
            MqMessage.MSG_ORDER_CANCEL,
            MqMessage.MSG_GOODS_ON,
            MqMessage.MSG_ORDER_SEND,
            MqMessage.MSG_GOODS_UPDATE

    );

    @RabbitHandler
    public void process(@Payload String content) {
        MqMessage mqMessage=JSON.parseObject(content,MqMessage.class);
        mqMessage.setHost(httpUrlConfig.getHost());
        String msgType = mqMessage.getMsgType();
        logger.info(MessageFormat.format("MqMessage==>{0}",mqMessage.toString()));
        if(StringUtils.isBlank(msgType) || !msgTypeList.contains(msgType)){
            return;
        }
        strategyContext.processNotifyByMsgType(mqMessage);
    }
}
