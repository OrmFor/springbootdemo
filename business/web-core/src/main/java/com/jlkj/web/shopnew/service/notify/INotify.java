package com.jlkj.web.shopnew.service.notify;


import com.jlkj.web.shopnew.yidto.message.bean.MqMessage;

public interface INotify {

    String processNotifyByMsgType(MqMessage mqMessage);

    String[] msgType();
}
