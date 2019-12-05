package com.jlkj.web.shopnew.service.notify;

import com.jlkj.web.shopnew.enums.EnumNotifyMsgType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class StrategyNotifyFactory implements InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(StrategyNotifyFactory.class);

    private static StrategyNotifyFactory factory = new StrategyNotifyFactory();


    @Autowired(required = false)
    private List<INotify> services;

    private static Map<String, INotify> map;


    @Override
    public void afterPropertiesSet() throws Exception {
        map = new HashMap<String, INotify>();
        if (CollectionUtils.isEmpty(services)){
            return;
        }
        for (INotify s : services) {
            for (String key : s.msgType()) {
                map.put(key, s);
            }
        }
    }

    public INotify creator(String msgType) {
        return getDelegate(msgType);
    }

    public INotify creator(EnumNotifyMsgType type) {
        return getDelegate(type.getMsgType());
    }

    public static StrategyNotifyFactory getInstance() {
        return factory;
    }


    private INotify getDelegate(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw new RuntimeException(String.format("StrategyNotifyFactory ： 暂不支持的业务种类%s", key));
    }
}
