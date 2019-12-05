package com.jlkj.web.shopnew.service.notify;

import com.jlkj.web.shopnew.yidto.message.bean.MqMessage;
import org.springframework.stereotype.Component;

/**
* @Author wwy
* @Description 根据所使用的类型生成相对应的订单
* @Date 9:35 2018/12/17
* @Param
* @return
**/
@Component
public class StrategyContext {

    private INotify strategy;

    public String processNotifyByMsgType(MqMessage mqMessage) {
        strategy = StrategyNotifyFactory.getInstance().creator(mqMessage.getMsgType());
        return strategy.processNotifyByMsgType(mqMessage);
    }
}
