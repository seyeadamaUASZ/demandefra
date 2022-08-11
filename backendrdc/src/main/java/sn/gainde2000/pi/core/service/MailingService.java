package sn.gainde2000.pi.core.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.tools.Email;

@Service
public class MailingService {
	
	@Autowired
	 Email email;
	private static String EMAIL_FROM="no-reply@gainde2000.sn";
	@Async
	public void sendEmail(String from,String to,String message,String subject) {
		try {
			email.sendMail(EMAIL_FROM, to, message, subject);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
