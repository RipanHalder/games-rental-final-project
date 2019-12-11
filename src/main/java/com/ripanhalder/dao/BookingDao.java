package com.ripanhalder.dao;

import java.util.List;

import com.ripanhalder.entity.Booking;
import com.ripanhalder.exception.BookingException;



public interface BookingDao {

	public Booking save(Booking booking) throws BookingException;

    public void delete(Booking booking) throws BookingException;
    
    public List<Booking> list() throws BookingException;
    
    public Booking getBooking(long bookingId) throws BookingException;
    
    public void update(Booking booking) throws BookingException;
    
    public float CalculateBookingAmount(Booking booking, float price) throws BookingException;
    
    void deleteBookingsByGameID(long gameId) throws BookingException;
    
//    List<Booking> listByUserId(long userId);
	
}
