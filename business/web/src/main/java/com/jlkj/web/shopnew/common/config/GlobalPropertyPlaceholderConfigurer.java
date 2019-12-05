package com.jlkj.web.shopnew.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Properties;

public interface GlobalPropertyPlaceholderConfigurer {

	//public Properties obtainProperties() throws IOException  ;

	
	public void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException;
	
}
