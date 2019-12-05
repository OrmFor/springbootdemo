//package com.jlkj.web.shopnew.configurer;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
//import com.jlkj.web.shopnew.interceptor.SecurityInterceptorNew;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.nio.charset.Charset;
//import java.util.List;
//
///**
// * @author huxin
// * @Date 2017年11月4日 下午6:12:01
// */
//@Configuration
//public class WebMvcConfigurerNew implements WebMvcConfigurer {
//
//	@Bean
//    SecurityInterceptorNew securityInterceptor() {
//		return new SecurityInterceptorNew();
//	}
//
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(securityInterceptor())
//                .addPathPatterns("/payapi/3.0/**")
//                .excludePathPatterns("/payapi/3.0/extend/notify")
//				.excludePathPatterns("/payapi/3.0/prepay/notify")
//                .excludePathPatterns("/payapi/3.0/bankrepay/notify");
//	}
//
///*    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
//		converters.add(converter);
//
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat
//        );
//
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        converters.add(fastConverter);
//    }*/
//
//    /**
//     * 跨域支持
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
//                .maxAge(3600 * 24);
//    }
//
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        // 1.定义一个converters转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        // 3.在converter中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        // 4.将converter赋值给HttpMessageConverter
//        HttpMessageConverter<?> converter = fastConverter;
//        // 5.返回HttpMessageConverters对象
//        return new HttpMessageConverters(converter);
//    }
//}