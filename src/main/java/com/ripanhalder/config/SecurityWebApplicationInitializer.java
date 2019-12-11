package com.ripanhalder.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//This registers ContextLoaderListener. Also registers DelegatingFilterProxy for using springSecurityFilterChain before any other registered filter.
public class SecurityWebApplicationInitializer 
						extends AbstractSecurityWebApplicationInitializer {

}
