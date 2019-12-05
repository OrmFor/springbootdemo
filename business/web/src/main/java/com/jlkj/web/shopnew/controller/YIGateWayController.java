package com.jlkj.web.shopnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.controller.base.BaseController;
import com.jlkj.web.shopnew.service.IOrders;
import com.jlkj.web.shopnew.service.IScoreGoods;
import com.jlkj.web.shopnew.service.notify.StrategyContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class YIGateWayController extends BaseController {

    @Autowired
    private IScoreGoods scoreGoodsService;

    @Autowired
    private IOrders ordersService;

    @Autowired
    private StrategyContext strategyContext;

    private static final Logger LOGGER = LogManager.getLogger(YIGateWayController.class);

    @RequestMapping("/notify")
    public void notifyFromMoneyButton() {
        JSONObject json = getJsonFromRequest();
        LOGGER.info("notify:::[json={}]", json);
        String msgType = json.getString("msgType");
        if(StringUtils.isBlank(msgType)){
            return;
        }
        //strategyContext.processNotifyByMsgType(json);

    }


}
