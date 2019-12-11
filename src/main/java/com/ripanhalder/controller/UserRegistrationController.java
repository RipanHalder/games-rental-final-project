package com.ripanhalder.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ripanhalder.user.RegisterationUser;
import com.ripanhalder.entity.User;
import com.ripanhalder.service.UserService;

/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 * @RequestMapping - It maps web requests to methods in request-handling classes
 */

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
	
    @Autowired
    private UserService userServiceObj;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor ste = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, ste);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("registerationUser", new RegisterationUser());
		
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("registerationUser") RegisterationUser newregisterationUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		String userName = newregisterationUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "registration-form";
	        }

		// Checking user already exists in the database
        User existing = userServiceObj.searchByUName(userName);
        if (existing != null){
        	theModel.addAttribute("registerationUser", new RegisterationUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "registration-form";
        } 
     // create user account        						
        userServiceObj.save(newregisterationUser);
        
        logger.info("Successfully created user: " + userName);
        	
        return "registration-confirmation";		
	}
}
