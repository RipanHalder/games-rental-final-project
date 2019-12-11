package com.ripanhalder.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ripanhalder.entity.Booking;
import com.ripanhalder.exception.BookingException;

@Repository
public class BookingDaoImpl implements BookingDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactObject;

	@Override
	@Transactional
	public Booking save(Booking booking) throws BookingException {
		try {// get the current hibernate session
			Session currentSession = sessionFactObject.getCurrentSession();

			// create the game
			currentSession.save(booking);
			return booking;
		} catch (HibernateException e) {
			throw new BookingException("Exception while creating booking: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void delete(Booking booking) throws BookingException {
		try {// get the current hibernate session
			Session currentSession = sessionFactObject.getCurrentSession();
			Booking bookingToDelete = (Booking) currentSession.merge(booking);
			// delete the game
			currentSession.delete(bookingToDelete);
		} catch (HibernateException e) {
			throw new BookingException("Exception while deleting booking: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<Booking> list() throws BookingException {
		try {
			Session currentSession = sessionFactObject.getCurrentSession();

			// now retrieve/read from database using name
			Query<Booking> theQuery = currentSession.createQuery("from Booking", Booking.class);
			List<Booking> allBookings = theQuery.getResultList();
			return allBookings;
		} catch (HibernateException e) {
			throw new BookingException("Exception while fetching all bookings: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public Booking getBooking(long bookingId) throws BookingException {
		try {
			Session currentSession = sessionFactObject.getCurrentSession();

			// now retrieve/read from database using name
			Query<Booking> theQuery = currentSession.createQuery("from Booking where bookingId= :bookingId",
					Booking.class);
			theQuery.setParameter("bookingId", bookingId);
			theQuery.setMaxResults(1);
			Booking booking = null;

			try {
				booking = theQuery.getSingleResult();
			} catch (Exception e) {
				booking = null;
			}

			return booking;
		} catch (HibernateException e) {
			throw new BookingException("Exception while fetching booking by bookingId: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Booking booking) throws BookingException {
		try {// get the current hibernate session
			Session currentSession = sessionFactObject.getCurrentSession();

			Booking entity = getBooking(booking.getBookingId());
			if (entity != null) {
				entity.setBookingDate(booking.getBookingDate());
				entity.setReturnDate(booking.getReturnDate());
				entity.setReturned(booking.getReturned());
				entity.setBookingAmount(booking.getBookingAmount());
				entity.setPaid(booking.getPaid());
				entity.setReturned(booking.getReturned());
			}
			currentSession.merge(entity);
		} catch (HibernateException e) {
			throw new BookingException("Exception while updating a booking: " + e.getMessage());
		}
	}

	@Override
	public float CalculateBookingAmount(Booking booking, float price) throws BookingException {
		try {
			long diffInMillies = Math.abs(booking.getReturnDate().getTime() - booking.getBookingDate().getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			float bookingAmount = 0;
			if (diff < 10) {
				bookingAmount = diff * price;
			}
			if (diff < 20 && diff >= 10) {
				diff += 1;
				bookingAmount = (float) (diff * price * 0.8);
			} else {
				diff += 1;
				bookingAmount = (float) (diff * price * 0.5);
			}
			return bookingAmount;
		} catch (HibernateException e) {
			throw new BookingException("Exception while calculating booking amount: " + e.getMessage());
		}
	}

	@Override
	public void deleteBookingsByGameID(long gameId) throws BookingException {
		try {// TODO Auto-generated method stub
			Session currentSession = sessionFactObject.getCurrentSession();
			Query<Booking> theQuery = currentSession.createQuery("from Booking b where b.game.gameId= :gameId",
					Booking.class);
			theQuery.setParameter("gameId", gameId);
			List<Booking> bookings = theQuery.getResultList();
			Booking bookingToDelete = null;
			for (Booking booking : bookings) {
				bookingToDelete = (Booking) currentSession.merge(booking);
				// delete the game
				currentSession.delete(bookingToDelete);
			}
		} catch (HibernateException e) {
			throw new BookingException("Exception while deleting a booking by gameId: " + e.getMessage());
		}
	}

}
