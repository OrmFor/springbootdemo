package java.base;

import com.alibaba.fastjson.JSONObject;
import com.jlkj.web.shopnew.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TestHttpClient  {
    public static void main(String[] args){

        CloseableHttpClient httpclient = HttpUtils.createWrapClient("https://cs47.51jlkj.com");
        HttpPost httppost = new HttpPost("https://cs47.51jlkj.com/api/1.0/getAddressDetail" );

        httppost.setHeader("content-type","application/json");
        httppost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36"
        );

        JSONObject paramJson = new JSONObject();
        paramJson.put("addressId","1031");
        StringEntity stringEntity = new StringEntity(paramJson.toJSONString(),ContentType.create("text/json", "UTF-8"));
        httppost.setEntity(stringEntity);
        String result = null;

        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            result = EntityUtils.toString(resEntity, "UTF-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
