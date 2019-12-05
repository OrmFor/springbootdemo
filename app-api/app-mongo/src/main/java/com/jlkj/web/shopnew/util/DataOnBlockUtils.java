package com.jlkj.web.shopnew.util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dyf on 2019/9/4 9:44
 * 数据上链
 */
public class DataOnBlockUtils {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(DataOnBlockUtils.class);
    public static final String serviceHost="http://127.0.0.1:8088";
    public static final String servicePlainDataUrl="/users/plainData";
    public static final String serviceSinglePicUrl="/upload/singlePic";
    public static final String servicePartPicUrl="/upload/partPic";
    public static final String servicePlanBigPicUrl="/users/bigPic";

    public static String plainDataOnChain(String data,String orderNo,String key){
        Map<String,String> headers= new HashMap();
        Map<String,String> body= new HashMap();
        body.put("data",data);
        body.put("orderno",orderNo);
        body.put("key",key);
        try {
            HttpResponse response = HttpUtils.doPost(serviceHost, servicePlainDataUrl, headers, null, body);
            String result = EntityUtils.toString(response.getEntity());
            LOGGER.info("数据上链返回："+result);
            if(200==response.getStatusLine().getStatusCode()){
                return result;
            }else{
              LOGGER.error("数据上链失败："+result);
            }
        } catch (Exception e) {
            LOGGER.error("数据上链出现异常："+e,e);
        }
        return null;
    }

    public static void main(String[] args) {
        String cityOnChain_hello_world = plainDataOnChain("Cityonchain life is going", "111222221111","KxebPxgshuYPHTmajJwH8fT8GWq25qiMdSTcw5wnpb1kudSfZZYd");
        System.out.println(cityOnChain_hello_world);
    }
}
