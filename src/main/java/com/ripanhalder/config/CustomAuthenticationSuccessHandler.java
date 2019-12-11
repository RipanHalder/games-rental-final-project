package com.ripanhalder.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ripanhalder.entity.User;
import com.ripanhalder.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userServiceObj;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("\n\nWe are currently in custom Authentication Success Handler\n\n");

		String userNameObj = authentication.getName();
		
		System.out.println("userName=" + userNameObj);

		User userObj = userServiceObj.searchByUName(userNameObj);
		
		// Put the user object in the session
		HttpSession session = request.getSession();
		session.setAttribute("user", userObj);
		
		// Redirect to the home page
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
