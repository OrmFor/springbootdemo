package com.jlkj.web.shopnew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.jlkj.web.shopnew"})
@ImportResource(locations = { "classpath*:spring-config.xml", "classpath*:spring-common.xml"})
@EnableTransactionManagement
public class APPApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(APPApplication.class, args);
	}

/*	@Configuration
	public class HttpConverterConfig {

		@Bean
		public HttpMessageConverters fastJsonHttpMessageConverters() {
			// 1.定义一个converters转换消息的对象
			FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
			// 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
			FastJsonConfig fastJsonConfig = new FastJsonConfig();
			fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
			// 3.在converter中添加配置信息
			fastConverter.setFastJsonConfig(fastJsonConfig);
			// 4.将converter赋值给HttpMessageConverter
			HttpMessageConverter<?> converter = fastConverter;
			// 5.返回HttpMessageConverters对象
			return new HttpMessageConverters(converter);
		}
	}*/

}
