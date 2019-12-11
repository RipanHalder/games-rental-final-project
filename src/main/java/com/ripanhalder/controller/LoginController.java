package com.ripanhalder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 */

@Controller
public class LoginController {

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/showMyLoginPage'
	 * Mapping type - GET
	 * Access - All
	 * Details - Opens the Login Form
	 * Returns - Login Form View
	 */
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {

		return "login-form";
		
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/access-denied'
	 * Mapping type - GET
	 * Access - All
	 * Details - Opens the Access Denied Page
	 * Returns - Access Denied View
	 */
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "access-denied";
		
	}
	
}









