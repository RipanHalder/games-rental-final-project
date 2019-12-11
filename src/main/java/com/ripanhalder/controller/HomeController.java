package com.ripanhalder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ripanhalder.dao.GameDao;
import com.ripanhalder.entity.Game;

/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 */

@Controller
public class HomeController {
	
	//Injecting GameDao object
	@Autowired
	GameDao gameDao;

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/'
	 * Mapping type - GET
	 * Access - All
	 * Details - Opens the Home Page
	 * Returns - Home View
	 */
	
	@GetMapping("/")
	public ModelAndView showHome() {
		ModelAndView mv = new ModelAndView();
		List<Game> g = gameDao.list();
		mv.addObject("games", gameDao.list());
		mv.setViewName("home");
		return mv;
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/aboutme'
	 * Mapping type - GET
	 * Access - All
	 * Details - Opens the about me page
	 * Returns - about me view
	 */
	
	@GetMapping("/aboutme")
	public String showAboutMePage() {
		return "meet-the-developer";
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/*'
	 * Mapping type - GET
	 * Access - Any
	 * Details - Opens the  non-existing pages
	 * Returns - Error Page
	 */
	
	@GetMapping("/*")
	public ModelAndView showErrorIfNotExists() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error-page");
		return mv;
	} 
}










