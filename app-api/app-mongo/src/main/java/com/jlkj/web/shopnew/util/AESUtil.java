package com.jlkj.web.shopnew.util;



import com.jlkj.web.shopnew.core.StatusCode;
import com.jlkj.web.shopnew.exception.BussinessException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法

	/**
	 * AES 加密操作
	 *
	 * @param content 待加密内容
	 * @param password 加密密码
	 * @return 返回Base64转码后的加密数据
	 */
	public static String encrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
			byte[] byteContent = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器
			byte[] result = cipher.doFinal(byteContent);// 加密
			//return Numeric.toHexString(result);
			 return new String(Base64.encodeBase64(result));// 通过Base64转码返回
		} catch (Exception e) {
			LOGGER.error("加密失败：", e);
			throw new BussinessException(StatusCode.ERROR_ENCRYPT);
		}
	}

	/**
	 * AES 解密操作
	 *
	 * @param content 待解密内容
	 * @param password 加密密码
	 * @return 返回解密数据
	 */
	public static String decrypt(String content, String password) {
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
			// 执行操作
			 byte[] result = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
			//byte[] result = cipher.doFinal(Numeric.hexStringToByteArray(content));
			return new String(result, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("解密失败：", e);
			throw new BussinessException(StatusCode.ERROR_DECRYPT);
		}
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		// 返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			// AES 要求密钥长度为 128
			kg.init(128, secureRandom);
			// 生成一个密钥
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
		} catch (Exception e) {
			LOGGER.error("生成加密秘钥失败：", e);
			throw new BussinessException(StatusCode.ERROR_SECRET_KEY);
		}
	}

	public static void main(String[] args) {
		String password = "$@quick#movie$666^";
		String encryptSign = AESUtil.encrypt("m1812120006", password);
		System.out.println("加密：" + encryptSign);
		String decryptSign = AESUtil.decrypt(encryptSign, password);
		System.out.println("解密：" + decryptSign);
	}

}
