package com.ripanhalder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ripanhalder.dao.PlatformDao;
import com.ripanhalder.entity.Platform;

/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 * @RequestMapping - It maps web requests to methods in request-handling classes
 */

@Controller
@RequestMapping("/platform/*")
public class PlatformController {

	//Injecting platformDao object
	@Autowired
	PlatformDao platformDao;
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/platform/create'
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Opens the new platform form
	 * Returns - create platform view
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createPlatform(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("platform", new Platform());
		mv.setViewName("create-platform");
		return mv;
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/platform/create'
	 * Mapping type - POST
	 * Access - Admins Only
	 * Details - Saves the new platform
	 * Returns - home view
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPlatform(@ModelAttribute("platform") Platform platform, HttpServletRequest request) {
		
		platformDao.create(platform);
		return new ModelAndView("home");
	}
}
