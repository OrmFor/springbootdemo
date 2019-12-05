//package com.jlkj.web.shopnew.configurer;
//
//import java.util.List;
//
//import com.jlkj.web.shopnew.interceptor.SecurityInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
//
///**
// * @author huxin
// * @Date 2017年11月4日 下午6:12:01
// */
//@Configuration
//public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
//
//	@Bean
//    SecurityInterceptor securityInterceptor() {
//		return new SecurityInterceptor();
//	}
//
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(securityInterceptor()).addPathPatterns("/payapi/3.0/**").excludePathPatterns("/payapi/3.0/extend/notify")
//				.excludePathPatterns("/payapi/3.0/prepay/notify").excludePathPatterns("/payapi/3.0/bankrepay/notify");
//	}
//
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
//		converters.add(converter);
//	}
//}