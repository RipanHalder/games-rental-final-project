package com.ripanhalder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ripanhalder.service.UserService;

/*@Configuration shows that a class can declare one or more @Bean methods. 
They might be processed using Spring container. It will generate service requests and bean definitions for such beans during the run time.
@EnableWebSecurity supports for Spring Security and by default is false.
*/
@Configuration
@EnableWebSecurity
public class GamesRentalSecurityConfig extends WebSecurityConfigurerAdapter {

	// Injecting by the user service object here.
    @Autowired
    private UserService userServiceObj;
	
    // Injecting the custom authentication success handler object here.
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    // Injecting by the user service object here.
   @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilderObj) throws Exception {
	   authenticationManagerBuilderObj.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity httpSecurityObj) throws Exception {

		httpSecurityObj.authorizeRequests()
			.antMatchers("/").hasRole("USER")
			.antMatchers("/game/create/**").hasRole("ADMIN")
			.antMatchers("/booking/showAllBookings").hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
		httpSecurityObj.authorizeRequests().antMatchers("/resources/css/**", "/resources/js/**", "/resources/images/**").permitAll();
		
	}
	

	//BCrypt Password Encoder bean definition below
	@Bean
	public BCryptPasswordEncoder pswdEncoder() {
		return new BCryptPasswordEncoder();
	}
 
	//Dao Authentication Provider bean definition below
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		//Create a new authentication provider object
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		//setting up custom user details service
		authenticationProvider.setUserDetailsService(userServiceObj);
		//setting up password encoder by BCrypt
		authenticationProvider.setPasswordEncoder(pswdEncoder()); 
		//Return the authentication provider object
		return authenticationProvider;
	}
	  
}






