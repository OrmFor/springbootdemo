package com.jlkj.web.shopnew.utils;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class TokenGenerator {

	public static String getToken(String username) {

		String salt = "";
		String token = DigestUtils.md5Hex(UUID.randomUUID().toString() + username);
		return token;
	}
}
