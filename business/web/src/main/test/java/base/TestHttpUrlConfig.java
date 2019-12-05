package java.base;

import com.jlkj.web.shopnew.common.HttpUrlConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestHttpUrlConfig extends base {

    @Autowired
    private HttpUrlConfig httpUrlConfig;

    @Test
    public void getUrl(){
        System.out.println(httpUrlConfig.getHost());
    }

}
