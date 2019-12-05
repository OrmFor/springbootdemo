package com.jlkj.web.shopnew.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {

	private static final Logger LOGGER = LogManager.getLogger(FileUtil.class);

	/**
	 * 
	 * @Title read
	 * @Description 读文本文件
	 * @author wzq
	 * @date 2018年8月15日 下午1:59:55
	 * @param fileName 读取路径
	 * @return
	 * @return String
	 */
	public static String read(String fileName) {
		StringBuffer buf = new StringBuffer();
		try {
			// 读取文件(字符流)
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
			// 读取数据，循环取出数据
			String str = null;
			while ((str = in.readLine()) != null) {
				buf.append(str);
			}
			// 关闭流
			in.close();
		} catch (Exception e) {
			LOGGER.error("读取失败：", e);
		}
		return buf.toString();
	}

	/**
	 * 
	 * @Title writer
	 * @Description 写文本文件
	 * @author wzq
	 * @date 2018年8月15日 下午1:59:22
	 * @param fileName 写入路径
	 * @param content 文件内容
	 * @return void
	 */
	public static void writer(String fileName, String content) {
		try {
			File file = new File(fileName);
			if (file.exists() && file.length() > 0) {
				LOGGER.info("已存在{}", fileName);
				return;
			}
			if (file.exists() && file.length() == 0) {
				LOGGER.info("已存在，数据为空{}", fileName);
				file.delete();
			}
			// 写入相应的文件
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "GBK"));
			// 写入相关文件
			out.write(content);
			out.newLine();
			// 清楚缓存
			out.flush();
			// 关闭流
			out.close();
		} catch (Exception e) {
			LOGGER.error("写入失败：", e);
		}
	}

}
