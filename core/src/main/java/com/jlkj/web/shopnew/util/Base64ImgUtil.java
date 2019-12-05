package com.jlkj.web.shopnew.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64ImgUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(Base64ImgUtil.class);

	/**
	 * 
	 * @Title getImageByte
	 * @Description 获取图片byte
	 * @author wzq
	 * @date 2018年12月20日 下午4:43:44
	 * @param imgStr base64编码字符串
	 * @return
	 * @return byte[]
	 */
	public static byte[] getImageByte(String imgStr) {
		if (imgStr == null)
			return null;
		if (imgStr.contains("data:image/jpeg;base64,"))
			imgStr = imgStr.replace("data:image/jpeg;base64,", "");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			return b;
		} catch (Exception e) {
			LOGGER.error("异常：", e);
			return null;
		}
	}

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		if (imgStr.contains("data:image/jpeg;base64,"))
			imgStr = imgStr.replace("data:image/jpeg;base64,", "");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			LOGGER.error("异常：", e);
			return false;
		}
	}

	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author:
	 * @CreateTime:
	 * @return
	 */
	public static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			LOGGER.error("异常：", e);
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

}