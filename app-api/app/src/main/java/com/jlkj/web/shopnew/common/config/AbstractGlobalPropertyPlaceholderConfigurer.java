package com.jlkj.web.shopnew.common.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

public abstract class AbstractGlobalPropertyPlaceholderConfigurer implements GlobalPropertyPlaceholderConfigurer,BeanNameAware, BeanFactoryAware {
	
	protected BeanFactory beanFactory;

	private String beanName;
	
	private PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}") ;

	public abstract Properties obtainProperties() throws IOException ;
	
	public void setBeanName(String name){
		this.beanName = name ;
	}
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException{
		this.beanFactory = beanFactory ;
	}

	public void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		
		StringValueResolver valueResolver = new GlobalPropertyPlaceholderStringValueResolver(props) ;
		BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);

		String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
		for (String curName : beanNames) {
			// Check that we're not parsing our own bean definition,
			// to avoid failing on unresolvable placeholders in properties file locations.
			if (!(curName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
				BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);
				try {
					visitor.visitBeanDefinition(bd);
				}
				catch (Exception ex) {
					throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName, ex.getMessage(), ex);
				}
			}
		}

		// New in Spring 2.5: resolve placeholders in alias target names and aliases as well.
		beanFactoryToProcess.resolveAliases(valueResolver);

		// New in Spring 3.0: resolve placeholders in embedded values such as annotation attributes.
		beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
		
	}
	
	
	private class GlobalPropertyPlaceholderStringValueResolver implements StringValueResolver {
		
		private  Properties properties ;
		
		public GlobalPropertyPlaceholderStringValueResolver(Properties properties){
			this.properties = properties ;
		}


		public String resolveStringValue(String strVal) throws BeansException {
			//System.out.println("---------" + strVal);
			
			if(strVal == null){
				return "" ;
			}
			strVal = helper.replacePlaceholders(strVal, properties) ;
			return strVal.trim() ;
		}
	}
	
	

}
