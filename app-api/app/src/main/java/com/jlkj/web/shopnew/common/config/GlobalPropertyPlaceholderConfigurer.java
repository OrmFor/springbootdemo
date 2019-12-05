package com.jlkj.web.shopnew.common.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public interface GlobalPropertyPlaceholderConfigurer {

	//public Properties obtainProperties() throws IOException  ;

	
	public void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException;
	
}
