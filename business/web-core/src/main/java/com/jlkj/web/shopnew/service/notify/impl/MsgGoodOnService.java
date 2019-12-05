package com.jlkj.web.shopnew.service.notify.impl;

import com.alibaba.fastjson.JSON;
import com.jlkj.web.shopnew.enums.EnumScoreGoodsStatus;
import com.jlkj.web.shopnew.pojo.ScoreGoods;
import com.jlkj.web.shopnew.service.IScoreGoods;
import com.jlkj.web.shopnew.service.notify.CreateNotifyServiceAdapter;
import com.jlkj.web.shopnew.yidto.message.bean.MqMessage;
import com.jlkj.web.shopnew.yidto.message.bean.YqsGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MsgGoodOnService extends CreateNotifyServiceAdapter {

    @Autowired
    private IScoreGoods scoreGoodsService;

    @Override
    public String processNotifyByMsgType(MqMessage mqMessage) {
        YqsGoods goods = JSON.parseObject(mqMessage.getContent(),YqsGoods.class);
        ScoreGoods condi = new ScoreGoods();
        condi.setGoodsId(goods.getGoodsId());

        ScoreGoods update = new ScoreGoods();
        update.setGoodsStatus(EnumScoreGoodsStatus.GOODS_ON.getStatus());

        scoreGoodsService.updateByCondition(update,condi);
        return "success";
    }

    @Override
    public String[] msgType() {
        return new String[]{
               MqMessage.MSG_GOODS_ON
        };
    }
}
