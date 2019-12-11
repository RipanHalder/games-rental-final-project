package com.ripanhalder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ripanhalder.dao.CategoryDao;
import com.ripanhalder.entity.Category;

/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 * @RequestMapping - It maps web requests to methods in request-handling classes
 */

@Controller
@RequestMapping("/category/*")
public class CategoryController {

	//Injecting categoryDao object
	@Autowired
	CategoryDao categoryDao;
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/category/create'
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Opens the new category form
	 * Returns - create category view
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createCategory(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("category", new Category());
		mv.setViewName("create-category");
		return mv;
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/category/create'
	 * Mapping type - POST
	 * Access - Admins Only
	 * Details - Saves the new category
	 * Returns - home view
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createCategory(@ModelAttribute("category") Category category, HttpServletRequest request) {
		
		categoryDao.create(category);
		return new ModelAndView("home");
	}
}
