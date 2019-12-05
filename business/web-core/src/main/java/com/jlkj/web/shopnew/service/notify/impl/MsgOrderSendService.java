package com.jlkj.web.shopnew.service.notify.impl;

import com.alibaba.fastjson.JSON;
import com.jlkj.web.shopnew.enums.EnumOrderStatus;
import com.jlkj.web.shopnew.pojo.ScoreOrder;
import com.jlkj.web.shopnew.service.IScoreOrder;
import com.jlkj.web.shopnew.service.notify.CreateNotifyServiceAdapter;
import com.jlkj.web.shopnew.yidto.message.bean.MqMessage;
import com.jlkj.web.shopnew.yidto.message.bean.YqsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MsgOrderSendService extends CreateNotifyServiceAdapter {

    @Autowired
    private IScoreOrder scoreOrderService;

    @Override
    public String processNotifyByMsgType(MqMessage mqMessage) {
        YqsOrder yqsOrder = JSON.parseObject(mqMessage.getContent(),YqsOrder.class);
        ScoreOrder condi = new ScoreOrder();
        condi.setOrderNum(yqsOrder.getOrder_num());

        ScoreOrder update = new ScoreOrder();
        update.setOrderStatus(EnumOrderStatus.WAIT_RECEIVING.getCode());

        update.setRemark(yqsOrder.getDesc());
        scoreOrderService.updateByCondition(update,condi);
        return "success";
    }

    @Override
    public String[] msgType() {
        return new String[]{
                MqMessage.MSG_ORDER_SEND
        };
    }
}
