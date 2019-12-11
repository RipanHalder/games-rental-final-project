package com.ripanhalder.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ripanhalder.dao.BookingDao;
import com.ripanhalder.dao.CategoryDao;
import com.ripanhalder.dao.GameDao;
import com.ripanhalder.dao.PlatformDao;
import com.ripanhalder.entity.Game;
import com.ripanhalder.entity.Platform;
import com.ripanhalder.service.CanvasjsChartService;
import com.ripanhalder.entity.Booking;
import com.ripanhalder.entity.Category;

/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 * @RequestMapping - It maps web requests to methods in request-handling classes
 */

@Controller
@RequestMapping("/game/*")
public class GameController {

	//Injecting GameDao object
	@Autowired
	GameDao gameDao;

	//Injecting CategoryDao object
	@Autowired
	CategoryDao categoryDao;

	//Injecting BookingDao object
	@Autowired
	BookingDao bookingDao;

	//Injecting PlatformDao object
	@Autowired
	PlatformDao platformDao;

	//Injecting CanvasjsChartService object
	@Autowired
	private CanvasjsChartService canvasjsChartService;

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/create'
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Opens the new game form
	 * Returns - The game form
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createGames() {
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject("categories", categoryDao.list());
			mv.addObject("platforms", platformDao.list());
			mv.addObject("game", new Game());
			mv.addObject("createOrUpdate", "create");
			mv.setViewName("game-form");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/create'
	 * Mapping type - POST
	 * Access - Admins Only
	 * Details - Adds the new game to the category and platform and saves the game.
	 * Returns - Redirects to the home page
	 */
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createGames(@ModelAttribute("game") Game game, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
//		Category c = categoryDao.getCategory(game.getCategory().getCategoryId());
			Category c = categoryDao.get(game.getCategory().getTitle());
			c.addGame(game);
			game.setCategory(c);
//		Platform p = platformDao.getPlatform(game.getPlatform().getPlatformId());
			Platform p = platformDao.get(game.getPlatform().getTitle());
			p.addGame(game);
			game.setPlatform(p);
			game.setCurrentRentalQuantity(0);
			gameDao.save(game);
			mv.setViewName("redirect:../");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/showFormForUpdate'
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Sends the category and platform lists and displays the game form in update mode
	 * Returns - View for game form
	 */

	@RequestMapping(value = "/showFormForUpdate", method = RequestMethod.GET)
	public ModelAndView updateGame(@RequestParam("gameId") long gameId) {
		ModelAndView mv = new ModelAndView();
		try {
			Game game = gameDao.getGame(gameId);
			mv.addObject("categories", categoryDao.list());
			mv.addObject("platforms", platformDao.list());
			mv.addObject("game", game);
			mv.setViewName("game-form");
			mv.addObject("createOrUpdate", "update");
			return mv;

		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/showFormForUpdate'
	 * Mapping type - POST
	 * Access - Admins Only
	 * Details - Saves the game
	 * Returns - Redirects to the home page
	 */

	@RequestMapping(value = "/showFormForUpdate", method = RequestMethod.POST)
	public ModelAndView updateGame(@ModelAttribute("game") Game game, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			gameDao.update(game);
			mv.setViewName("redirect:../");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/showAllGames'
	 * Mapping type - GET
	 * Access - All
	 * Details - binds the list of all games
	 * Returns - all games view
	 */

	@RequestMapping(value = "/showAllGames", method = RequestMethod.GET)
	public ModelAndView showAllGames() {
		ModelAndView mv = new ModelAndView();
		try {
			List<Game> allGames = gameDao.list();
			mv.addObject("allGames", allGames);
			mv.setViewName("all-games");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/showGame?gameId='
	 * Mapping type - GET
	 * Access - All
	 * Details - Binds the game by fetching through the gameId
	 * Returns - Game Detail View
	 */

	@RequestMapping(value = "/showGame", method = RequestMethod.GET)
	public ModelAndView showGame(@RequestParam("gameId") long gameId) {
		ModelAndView mv = new ModelAndView();
		try {
			Game game = gameDao.getGame(gameId);
			mv.addObject("game", game);
			mv.setViewName("game-detail");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/deleteGame?gameId='
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Fetches the game Id and deletes it
	 * Returns - Redirects to All Games View
	 */

	@RequestMapping(value = "/deleteGame", method = RequestMethod.GET)
	public ModelAndView deleteGame(@RequestParam("gameId") long gameId) {
		ModelAndView mv = new ModelAndView();
		try {
			Game game = gameDao.getGame(gameId);
//		bookingDao.deleteBookingsByGameID(gameId);
			gameDao.delete(game);
			mv.setViewName("redirect:/game/showAllGames");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/game/games-sales-chart'
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Displays an interactive Sales UI. Reference - https://canvasjs.com/
	 * Returns - games-sales-chart view
	 */

	@RequestMapping(value = "/games-sales-chart", method = RequestMethod.GET)
	public String springMVC(ModelMap modelMap) {
		try {
			List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData();

			modelMap.addAttribute("dataPointsList", canvasjsDataList);
			return "games-sales-chart";

		} catch (Exception e) {
			return "error-page";
		}
	}
}
