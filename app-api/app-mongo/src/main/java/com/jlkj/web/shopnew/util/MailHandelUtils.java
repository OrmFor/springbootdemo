package com.jlkj.web.shopnew.util;


import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import cc.s2m.web.utils.webUtils.StaticProp;

import javax.mail.internet.MimeMessage;

/**
 * 邮件相关统一公用类
 */
public class MailHandelUtils extends JavaMailSenderImpl {
	private static final Logger LOGGER = LogManager.getLogger(MailHandelUtils.class);

	private static JavaMailSender MAIL_SENDER;

	static {
		// 初始化邮件工具
		MAIL_SENDER = new MailHandelUtils();
	}

	private MailHandelUtils() {
		this.setProtocol("smtp");
		this.setHost(StaticProp.sysConfig.get("smtp.host"));
		this.setPort(Integer.parseInt(StaticProp.sysConfig.get("smtp.port")));
		this.setUsername(StaticProp.sysConfig.get("smtp.username"));
		this.setPassword(StaticProp.sysConfig.get("smtp.pwd"));

		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", StaticProp.sysConfig.get("smtp.auth"));
		javaMailProperties.setProperty("mail.smtp.starttls.enable", StaticProp.sysConfig.get("smtp.starttls"));
		javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		this.setJavaMailProperties(javaMailProperties);
	}

	public static void sendTextMail(final String email, final String title, final String text) {
		StaticProp.execute.submit(new Runnable() {
			public void run() {
				try {
					MimeMessage mimeMsg = MAIL_SENDER.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "GBK");
					helper.setFrom(StaticProp.sysConfig.get("smtp.mail"));
					helper.setTo(email);
					helper.setSubject(title);
					helper.setText(text, false);
					MAIL_SENDER.send(mimeMsg);
				} catch (Exception e) {
					LOGGER.error("", e);
				}
			}
		});
	}

	public static void sendHtmlMail(final String email, final String title, final String html) {
		StaticProp.execute.submit(new Runnable() {
			public void run() {
				try {
					MimeMessage mimeMsg = MAIL_SENDER.createMimeMessage();
					mimeMsg.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
					//mimeMsg.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(FROM));
					MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "GBK");
					helper.setFrom(StaticProp.sysConfig.get("smtp.mail"), "Cityonchain");
					helper.setTo(email);
					helper.setSubject(title);
					helper.setText(html, true);
					MAIL_SENDER.send(mimeMsg);
				} catch (Exception e) {
					LOGGER.error("", e);
				}
			}
		});
	}
}
