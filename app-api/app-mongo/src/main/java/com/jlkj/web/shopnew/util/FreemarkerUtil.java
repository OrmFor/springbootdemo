package com.jlkj.web.shopnew.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 工具类-freemarker的模板处理
 */
public class FreemarkerUtil {
	private static final Logger LOGGER = LogManager.getLogger(FreemarkerUtil.class);

	public static Configuration CONFIG;

	public static String renderTemplate(String s, Map<String, Object> data) {
		try {
			Template t = new Template(null, new StringReader(s), CONFIG);
			// 执行插值，并输出到指定的输出流中
			StringWriter w = new StringWriter();
			t.getConfiguration();
			t.process(data, w);
			return w.getBuffer().toString();
		} catch (Exception e) {
			LOGGER.error("填充模板内容出现异常，模板：" + s, e);
			return null;
		}
	}

	public static String renderFileTemplate(String file, Map<String, Object> data) {
		try {
			Configuration cfg = CONFIG;
			cfg.setDefaultEncoding("UTF-8");
			// 取得模板文件
			Template t = cfg.getTemplate(file);
			// 执行插值，并输出到指定的输出流中
			StringWriter w = new StringWriter();
			t.getConfiguration();
			t.process(data, w);
			return w.getBuffer().toString();
		} catch (Exception e) {
			LOGGER.error("填充模板内容出现异常，模板：" + file, e);
			return null;
		}
	}

	public static String renderFileTemplate(String tmpPath, String file, Map<String, Object> data) {
		try {
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			cfg.setDefaultEncoding("UTF-8");
			cfg.setDirectoryForTemplateLoading(new File(tmpPath));
			// 取得模板文件
			Template t = cfg.getTemplate(file);
			// 执行插值，并输出到指定的输出流中
			StringWriter w = new StringWriter();
			t.getConfiguration();
			t.process(data, w);
			return w.getBuffer().toString();
		} catch (Exception e) {
			LOGGER.error("填充模板内容出现异常，模板：" + file, e);
			return null;
		}
	}

}
