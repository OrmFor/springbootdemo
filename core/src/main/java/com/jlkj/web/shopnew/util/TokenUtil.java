package com.jlkj.web.shopnew.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by dyf on 2019/7/20 15:17
 */
public class TokenUtil {
    public static String getTokenString(String token)  {
        if(StringUtil.isBlank(token)){
            return null;
        }
        String jsonStr = null;
        try {
            jsonStr = new String(Base64.decodeBase64(token), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject json = JSONObject.parseObject(jsonStr);
        return json.getString("token");
    }
}
