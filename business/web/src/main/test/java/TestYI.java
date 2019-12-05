package java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

public class TestYI {

    private static final String url = "http://47.95.119.153:8808/third/address/add";
        public static void main(String[] args) throws Exception {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpPost httppost = new HttpPost(url);
                String areaInfo="绍兴柯桥蓝天广场1单元501";
                String moblie="18815699357";
                String trueName="测试11";
                String zip="312000";
                String areaId= "330600";//获取系统时间戳
                String userId= "2";//获取系统时间戳

                ContentType strContent=ContentType.create("text/plain",Charset.forName("UTF-8"));
                HttpEntity reqEntity  = MultipartEntityBuilder.create()
                        .addTextBody("areaInfo",areaInfo,strContent)
                        .addTextBody("moblie",moblie,strContent)
                        .addTextBody("trueName",trueName,strContent)
                        .addTextBody("zip",zip,strContent)
                        .addTextBody("areaId",areaId,strContent)
                        .addTextBody("userId",userId,strContent)
                        .build();


                httppost.setEntity(reqEntity);
                httppost.setHeader("chartSet","UTF-8");


                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result=EntityUtils.toString(resEntity,"UTF-8");
                        //打印获取到的返回值
                        System.out.println("Response content: " + result);
                        JSONObject json = JSONObject.parseObject(result);
                        JSONObject jsonObject = json.getJSONObject("data");
                        Integer addressId = jsonObject.getInteger("id");
                        System.out.println(addressId);





                    }
                   // EntityUtils.consume(resEntity);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        }


}
