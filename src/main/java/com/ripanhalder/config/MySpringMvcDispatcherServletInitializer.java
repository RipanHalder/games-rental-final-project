package com.ripanhalder.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// We never call this method. It is used to fetch all the root config classes.
		return null;
	}

	//To bind with the main configuration class
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { GamesRentalApplicationConfig.class };
	}

	//This returns all servlet mappings after '/'
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}






