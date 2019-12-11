package com.ripanhalder.service;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.ripanhalder.entity.Booking;

@Service
public class EmailServiceImpl implements MailService{

	@Autowired
	JavaMailSender mailSender;
	
	@Override
	public void sendNewOrderEmail(Object object) {
		Booking booking = (Booking) object;
		 
        MimeMessagePreparator preparator = getNewOrderMessagePreparator(booking);
 
        try {
            mailSender.send(preparator);
            System.out.println("Message Send!");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
	}

	private MimeMessagePreparator getNewOrderMessagePreparator(final Booking booking) {
		 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom("ripanh4@gmail.com");
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(booking.findUserByUserId().getEmail()));
                mimeMessage.setText("Dear " + booking.findUserByUserId().getFirstName()
                        + ",\nThank you for placing your order for "+ booking.getGame().getTitle() +". Last Date to return the game is on "+ booking.getReturnDate().getDate()+". \n\n Thanks and Regards, \n Games Rental Store.");
                mimeMessage.setSubject("New order placed on Games Rental!");
            }
        };
        return preparator;
    }
	
	private MimeMessagePreparator getReturnedNotificationMessagePreparator(final Booking booking) {
		 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom("ripanh4@gmail.com");
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(booking.findUserByUserId().getEmail()));
                mimeMessage.setText("Dear " + booking.findUserByUserId().getFirstName()
                        + ",\nThank you for using our service. You game "+ booking.getGame().getTitle() +"has been succesfully returned. \n\nThanks and Regards, \nGames Rental Store.");
                mimeMessage.setSubject("Order Returned - Games Rental!");
            }
        };
        return preparator;
    }

	@Override
	public void sendReturnedNotificationEmail(Object object) {
		Booking booking = (Booking) object;
		 
        MimeMessagePreparator preparator = getReturnedNotificationMessagePreparator(booking);
 
        try {
            mailSender.send(preparator);
            System.out.println("Message Send!");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
	}

}
