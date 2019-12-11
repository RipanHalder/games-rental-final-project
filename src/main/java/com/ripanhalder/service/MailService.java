package com.ripanhalder.service;


public interface MailService {

	public void sendReturnedNotificationEmail(final Object object);
	
	public void sendNewOrderEmail(final Object object);
	
}
