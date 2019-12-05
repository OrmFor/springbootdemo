//package com.jlkj.web.shopnew.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.base.Joiner;
//import com.jlkj.web.shopnew.common.config.ConfigUtil;
//import com.jlkj.web.shopnew.utils.redis.RedisUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.Errors;
//
//import java.util.concurrent.TimeUnit;
//
//
///**
// * @author huxin
// * @Date 2017年7月18日 上午11:13:00
// */
//public class SessionUtil {
//
//	private static final Logger LOGGER = LogManager.getLogger(SessionUtil.class);
//	private String prefix = "session";
//	private RedisUtil redisUtil;
//
//	public void setRedisUtil(RedisUtil redisUtil) {
//		this.redisUtil = redisUtil;
//	}
//
//	/**
//	 * 从Session中获取用户信息
//	 *
//	 * @param token
//	 * @return
//	 */
//	public User getUserInfo(String token) {
//		String mobilKey = Joiner.on(".").join(prefix, "app", token);
//		LOGGER.info("mobilKey:" + mobilKey);
//		String usersStr = redisUtil.find(mobilKey);
//		LOGGER.info("usersStr:" + usersStr);
//		if (StringUtils.isEmpty(usersStr)) {
//			return null;
//		}
//		return JSON.parseObject(usersStr, Users.class);
//	}
//
//	/**
//	 * 登录成功后，在session中设置用户信息
//	 *
//	 * @param users
//	 * @param token
//	 */
//	public void setUserInfo(User users, String token) {
//		Assert.notNull(users);
//		Assert.notNull(token);
//		long timeout = Long.parseLong(ConfigUtil.getConfigValue("p2p.session.timeout.long"));
//		String key = Joiner.on(".").join(prefix, "app", token);
//		redisUtil.addWithTimeout(key, JSON.toJSONString(users), timeout, TimeUnit.SECONDS);
//	}
//
//	/**
//	 * 清除用户Session
//	 *
//	 * @param token
//	 */
//	public void deleteToken(String token) {
//		String mobilKey = Joiner.on(".").join(prefix, "app", token);
//		redisUtil.delete(mobilKey);
//	}
//
//	/**
//	 * 重置用户的session超时时间
//	 *
//	 * @param token
//	 */
//	public void resetSessionTimeout(String token) {
//		long timeout = Long.parseLong(ConfigUtil.getConfigValue("p2p.session.timeout.long"));
//		String key = Joiner.on(".").join(prefix, "app", token);
//		if (!redisUtil.exist(key)) {
//			throw new BussinessExceptionNew(Errors.commonError.userNotLoginOrExpiredError);
//		}
//		redisUtil.updateTimeout(key, timeout, TimeUnit.SECONDS);
//	}
//
//	/**
//	 * 是否存在登录的token
//	 *
//	 * @param token
//	 */
//	public Boolean existLoginToken(String token) {
//		String key = Joiner.on(".").join(prefix, "app", token);
//		return redisUtil.exist(key);
//	}
//
//	/**
//     * 清除用户Session
//     *
//     * @param token
//     */
//    public void clean(String token) {
//        String pcKey = Joiner.on(".").join(prefix, "pc", token);
//        String mobilKey = Joiner.on(".").join(prefix, "app", token);
//        redisUtil.delete(pcKey);
//        redisUtil.delete(mobilKey);
//    }
//
//}
