package com.jlkj.web.shopnew.common;

import com.jlkj.web.shopnew.controller.base.BaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecurityInterceptorNew extends BaseController implements HandlerInterceptor{

	private static final Logger LOGGER = LogManager.getLogger(SecurityInterceptorNew.class);


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String method = request.getMethod();

		if (!("post".equals(method.toLowerCase()))) {
			return true;
		}

		/*Map<String, String> params = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}

			params.put(name, valueStr);
			jsonObject.put(name, valueStr);
		}
		LOGGER.info("path=" + request.getServletPath());
		LOGGER.info("参数：" +JSONObject.toJSONString(jsonObject));
*/

		/*Boolean result = true;
		ErrorObject errorObject = null;
		response.setContentType("application/json;charset=utf-8");
		response.addHeader("Content-Type", "application/json;charset=utf-8");
		// 获取 header中的device、version、token、platform参数
		String platform = request.getHeader("platform");
		String version = request.getHeader("version");
		String deviceId = request.getHeader("deviceId");
		String token = request.getHeader("token");
		try {
			// 判断platform、version、deviceId输入是否为空
			if (StringUtils.isBlank(platform) || StringUtils.isBlank(version) || StringUtils.isBlank(deviceId) || token == null) {
				throw new BussinessExceptionNew(Errors.commonError.headerParamsEmptyException);
			}
			// 判断platform输入是否正确
			if (mobilePlatformCache.get(platform) == null) {
				throw new BussinessExceptionNew(Errors.commonError.platformParamCheckException);
			}
			// 判断token是否为空
			if (StringUtils.isNotBlank(token)) {
				// 如果token不存在则抛出异常
//				if (!sessionUtil.existLoginToken(token)) {
//					throw new BussinessExceptionNew(Errors.commonError.tokenNotExistException);
//				} else { // 重置token的超时时间
//					sessionUtil.resetSessionTimeout(token);
//				}
			}
		} catch (BussinessExceptionNew e) {
			LOGGER.error(e.getMessage(), e);
			result = false;
			errorObject = new ErrorObject(e.getCode(), e.getName(), e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			result = false;
			errorObject = Errors.SystemError.systemException;
		}
		if (!result) {
			ResponseResult responseResult = ErrorUtil.errorProcess(errorObject);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(JSON.toJSONString(responseResult));
			} finally {
				if (out != null) {
					out.flush();
					out.close();
				}
			}
		}*/
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		response.setContentType("application/json");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		response.setContentType("application/json");
	}

}
