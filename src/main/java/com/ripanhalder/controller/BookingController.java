package com.ripanhalder.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ripanhalder.dao.BookingDao;
import com.ripanhalder.dao.GameDao;
import com.ripanhalder.dao.UserDao;
import com.ripanhalder.entity.Booking;
import com.ripanhalder.entity.Game;
import com.ripanhalder.entity.User;
import com.ripanhalder.exception.BookingException;
import com.ripanhalder.service.MailService;
import com.ripanhalder.service.PayPalService;
/*
 * @Controller - It is a special type @Component which allows the implementation of classes and helps to autodetect through classpath scanning
 * @RequestMapping - It maps web requests to methods in request-handling classes
 * @Scope - It returns the name of the scope for the below controller.
 */
@Controller
@RequestMapping("/booking")
@Scope("session")
public class BookingController {

	//Injecting GameDao object
	@Autowired
	GameDao gameDao;

	//Injecting UserDao object
	@Autowired
	UserDao userDao;

	//Injecting BookingDao object
	@Autowired
	BookingDao bookingDao;

	//Injecting PayPalService object
	@Autowired
	private PayPalService payPalService;

	//Injecting MailService object
	@Autowired
	MailService mailService;
	
	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/showBookingForm'
	 * Mapping type - GET
	 * Access - All
	 * Details - Opens the booking form
	 * Returns - View for the booking form
	 */ 

	@RequestMapping(value = "/showBookingForm", method = RequestMethod.GET)
	public ModelAndView showBookingForm(@RequestParam("gameId") long gameId, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {

			mv.addObject("gameId", gameId);
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			mv.addObject("userId", u.getId());
			mv.setViewName("booking-form");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/showBookingForm'
	 * Mapping type - POST
	 * Access - All
	 * Details - Submits the booking form. Creates a new booking, sets its values, adds the booking to the 
	 * 			 respective User and Game and saves the booking.
	 * Returns - Redirects to my bookings view
	 */
	@RequestMapping(value = "/showBookingForm", method = RequestMethod.POST)
	public ModelAndView showBookingForm( 
			@RequestParam("gameId") long gameId, 
			HttpServletResponse response,
			HttpServletRequest request) throws BookingException {
		ModelAndView mv = new ModelAndView();
		try {
			Booking booking = new Booking();
			booking.setBookingDate(new Date());  
			Date returnDate=new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("returnDate")); 
			booking.setReturnDate(returnDate);
			Game game = gameDao.getGame(gameId);
			booking.setBookingAmount(bookingDao.CalculateBookingAmount(booking, game.getPrice()));
			game.addBooking(booking);
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			User user = userDao.findUserByUserId(u.getId());
			user.addBooking(booking);
			booking.setReturned("No");
			booking.setPaid("No");
			bookingDao.save(booking);
			gameDao.update(game);
			Cookie c = new Cookie("bookingId", Long.toString(booking.getBookingId()));
			response.addCookie(c);
//		request.getSession().setAttribute("bookingId", booking.getBookingId());
			mv.setViewName("redirect:/booking/myBookings?userId=" + u.getId());
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/success'
	 * Mapping type - GET
	 * Access - All
	 * Details - On successful payment from PayPal gateway, hits this controller. Fetches the booking id 
	 * 			 from cookie and updates the booking and game. Sends a successful booking email notification
	 * 			 to logged in user.
	 * Returns - 
	 */
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView success(HttpServletRequest request, HttpServletResponse response) throws BookingException {
		ModelAndView mv = new ModelAndView();
		try {
			Cookie[] cookies = request.getCookies();
			long bookingId = 0;
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("bookingId")) {
					bookingId = Long.parseLong(cookies[i].getValue());
					break;
				}
			}
			Booking booking = bookingDao.getBooking(bookingId);
			booking.setPaid("Yes");
			Game game = gameDao.getGame(booking.getGame().getGameId());
			game.setQuanity(game.getQuanity() - 1);
			game.setCurrentRentalQuantity(game.getCurrentRentalQuantity() + 1);
			bookingDao.update(booking);
			gameDao.update(game);
			mailService.sendNewOrderEmail(booking);
			request.getSession().removeAttribute("bookingId");
			mv.setViewName("payment-successfull");

			
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/myBookings'
	 * Mapping type - GET
	 * Access - All
	 * Details - Shows the list of all my bookings
	 * Returns - View for my bookings
	 */
	
	@RequestMapping(value = "/myBookings", method = RequestMethod.GET)
	public ModelAndView showMyBookings(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			User user = userDao.findUserByUserId(u.getId());
			Set<Booking> myBookings = user.getBookings();
			mv.addObject("payPalConfig", payPalService.getPayPalConfig());
//		List<Booking> myBookings = bookingDao.listByUserId(userId);
			mv.addObject("myBookings", myBookings);
			mv.setViewName("user-bookings");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/deleteBooking'
	 * Mapping type - GET
	 * Access - Admins Only
	 * Details - Deletes a booking by the given booking ID and from the logged in user.
	 * Returns - Redirects to my Bookings page
	 */
	
	@RequestMapping(value = "/deleteBooking", method = RequestMethod.GET)
	public ModelAndView deleteBooking(@RequestParam("gameId") long gameId, @RequestParam("bookingId") long bookingId,
			HttpServletRequest request) throws BookingException {
		ModelAndView mv = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			Game game = gameDao.getGame(gameId);
			Booking booking = bookingDao.getBooking(bookingId);
			if(booking.getPaid().equals("Yes") && booking.getReturned().equals("No")) {
				game.setQuanity(game.getQuanity() + 1);
				game.setCurrentRentalQuantity(game.getCurrentRentalQuantity() - 1);
			}
			bookingDao.delete(booking);
			gameDao.update(game);
			mv.setViewName("redirect:/booking/myBookings?userId=" + u.getId());
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/deleteFromAllBookings'
	 * Mapping type - GET
	 * Access - Admins only
	 * Details - Deletes a booking by the given booking ID and from the logged in user.
	 * Returns - Redirects to all bookings view
	 */
	
	@RequestMapping(value = "/deleteFromAllBookings", method = RequestMethod.GET)
	public ModelAndView deleteFromAllBookings(@RequestParam("gameId") long gameId,
			@RequestParam("bookingId") long bookingId) throws BookingException {
		ModelAndView mv = new ModelAndView();
		try {
			Game game = gameDao.getGame(gameId);
			Booking booking = bookingDao.getBooking(bookingId);
			if(booking.getPaid().equals("Yes") && booking.getReturned().equals("No")) {
				game.setQuanity(game.getQuanity() + 1);
				game.setCurrentRentalQuantity(game.getCurrentRentalQuantity() - 1);
			}
			bookingDao.delete(booking);
			gameDao.update(game);
			mv.setViewName("redirect:/booking/showAllBookings");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/returnBooking'
	 * Mapping type - GET
	 * Access - All
	 * Details - Returns the booking, increases the game quantity and decreases the rental quantity
	 * Returns - Redirects to my bookings
	 */
	
	@RequestMapping(value = "/returnBooking", method = RequestMethod.GET)
	public ModelAndView returnBooking(@RequestParam("gameId") long gameId, @RequestParam("bookingId") long bookingId,
			HttpServletRequest request) throws BookingException {
		ModelAndView mv = new ModelAndView();
		try {
			Game game = gameDao.getGame(gameId);
			game.setQuanity(game.getQuanity() + 1);
			game.setCurrentRentalQuantity(game.getCurrentRentalQuantity() - 1);
			Booking booking = bookingDao.getBooking(bookingId);
			booking.setReturned("Returned");
			bookingDao.update(booking);
			gameDao.update(game);
			mailService.sendReturnedNotificationEmail(booking);
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			mv.setViewName("redirect:/booking/myBookings?userId=" + u.getId());
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

	/*
	 * Mapping by - 'http://localhost:8080/games-rental-final-project/booking/showAllBookings'
	 * Mapping type - GET
	 * Access - All
	 * Details - Fetches list of all bookings
	 * Returns - All bookings view
	 */
	
	@RequestMapping(value = "/showAllBookings", method = RequestMethod.GET)
	public ModelAndView showAllBookings() throws BookingException {
		ModelAndView mv = new ModelAndView();
		try {
			List<Booking> allBookings = bookingDao.list();
			mv.addObject("allBookings", allBookings);
			mv.setViewName("all-bookings");
			return mv;
		} catch (Exception e) {
			mv.setViewName("error-page");
			return mv;
		}
	}

}
