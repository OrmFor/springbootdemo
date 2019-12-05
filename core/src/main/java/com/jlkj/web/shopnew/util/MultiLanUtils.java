package com.jlkj.web.shopnew.util;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * Created by dyf on 2019/6/21 13:51
 * 国际化信息获取
 */
public class MultiLanUtils {
    private static ResourceBundleMessageSource messageSource;

    public static void setMessageSource(ResourceBundleMessageSource messageSource) {
        MultiLanUtils.messageSource = messageSource;
    }

    /**获取配置地区信息*/
    public static String getMessage(String code,Locale locale){
        return messageSource.getMessage(code,null,locale);
    }
    /**获取格式化地区信息*/
    public static String getFormatMessage(String code, Object[] params, Locale locale){
        return messageSource.getMessage(code,params,locale);
    }
    /**获取中文信息*/
    public static String getChinaMessage(String code){
        return messageSource.getMessage(code,null,Locale.CHINA);
    }
    /**获取英文信息*/
    public static String getUSMessage(String code){
        return messageSource.getMessage(code,null,Locale.US);
    }
}
