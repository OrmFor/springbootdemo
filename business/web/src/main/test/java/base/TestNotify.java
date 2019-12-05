package java.base;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.enums.EnumNotifyMsgType;
import com.jlkj.web.shopnew.service.notify.StrategyContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestNotify extends base {


    @Autowired
    private StrategyContext strategyContext;

    @Test
    public void test(){
        JSONObject json = new JSONObject();
        json.put("msyType",EnumNotifyMsgType.MSG_ORDER_SEND.getMsgType());
       // strategyContext.processNotifyByMsgType(json);
    }
}
