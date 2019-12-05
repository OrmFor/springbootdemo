package com.jlkj.web.shopnew.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by dyf on 2019/3/18 9:14
 * 汉语拼音工具包
 */
public class PinYinUtils {
    private static InputStream inputStream;
    /**获取汉语拼音*/
    public static String getPingYin(String src) {
        char[] inputChar = null;
        inputChar = src.toCharArray();
        int inputCharLength = inputChar.length;
        String[] piword = new String[inputCharLength];
        HanyuPinyinOutputFormat hPinyinOutputFormat = new HanyuPinyinOutputFormat();
        //大小写
        hPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String piStr = "";
        try {
            for (int i = 0; i < inputCharLength; i++) {
                // 判断是否为汉字字符
                if (Character.toString(inputChar[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    piword = PinyinHelper.toHanyuPinyinStringArray(inputChar[i], hPinyinOutputFormat);
                    piStr += piword[0];
                } else
                    piStr += Character.toString(inputChar[i]);
            }
            return piStr;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return piStr;
    }
    public static Character getFirstChar(String src){
        String pingYin = getPingYin(src);
        char c = pingYin.toUpperCase().charAt(0);
        return c;
    }
}
