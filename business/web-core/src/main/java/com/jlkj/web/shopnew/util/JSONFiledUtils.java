package com.jlkj.web.shopnew.util;

/**
 * Created by dyf on 2019/8/29 11:25
 */
public class JSONFiledUtils {
    public final static String[] words=new String[]{",","\\[",":","]","\\{","}","\\\\"};
    public static String replaceSensitiveChar(String jsonFiled){
        String replace = jsonFiled.replace("\"", "\'");
        for(int i=0;i<words.length;i++){
            replace=replace.replaceAll(words[i],"\\\\"+words[i]);
        }
        replace=replace.replaceAll("https\\\\\\\\:","https:");
        replace=replace.replaceAll("http\\\\\\\\:","http:");
        return replace;
    }

    public static String backSensitiveChar(String jsonFiled){
        String replace = jsonFiled.replace("\'", "\"");
        for(int i=0;i<words.length;i++){
            replace=replace.replaceAll("\\\\"+words[i],words[i]);
        }
        return replace;
    }

    public static void main(String[] args) {
        System.out.println(replaceSensitiveChar("https:ahah,s]\\[[[[[****"));
    }
}
