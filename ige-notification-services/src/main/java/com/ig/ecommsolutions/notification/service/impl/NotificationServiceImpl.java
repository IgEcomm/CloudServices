package com.ig.ecommsolutions.notification.service.impl;

import java.io.File;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.notification.constants.Constants;
import com.ig.ecommsolutions.notification.dto.EmailDTO;
import com.ig.ecommsolutions.notification.dto.MessageDTO;
import com.ig.ecommsolutions.notification.dto.PushNotificationDTO;
import com.ig.ecommsolutions.notification.service.NotificationService;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;


@Service
public class NotificationServiceImpl implements NotificationService {
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${deviceToken}")
	private String deviceToken;
	@Value("${from}")
	private String from;
	
	private ApnsService service;

   @Override
	public ResponseEntity<?> pushNotification(PushNotificationDTO notification){
	   log.info("push Notification Service called...");
		try{
			File p12file = new File("src/main/resources/IGCommerceDevPushCert.p12");
			service =  APNS.newService().withCert(p12file.getAbsolutePath(),"rajput")
						.withSandboxDestination().build();	
			service.start();
			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder.badge(Constants.BADGE_KEY).sound(Constants.DEFAULT_MESSAGE_KEY).build();
			payloadBuilder.alertBody(notification.getNotification());
			String payload = payloadBuilder.build(); 
			service.push(deviceToken, payload);
			log.info("Push notification Success!!! ");
			return new ResponseEntity<MessageDTO>(new MessageDTO("Alert has been successfully send:: "+notification.getNotification()), HttpStatus.OK);
		}catch(Exception e){
			log.error("Error in Push Notification due to ::" +  e.getMessage());
			return new ResponseEntity<MessageDTO>(new MessageDTO("Push Notification fails"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/* 
	 * email Notification
	 * 
	 * */
	
	@Override
	public ResponseEntity<?> emailNotification(EmailDTO emailobj)  {
		try{
			 sendEmail(emailobj);
			 log.info("emailNotification  successfullly executed");
			 return  new ResponseEntity<MessageDTO>(new MessageDTO("email Notification successfull"), HttpStatus.OK);
		}catch(Exception e){
			 log.info("emailNotification failed	"+e.getMessage());
			 return new ResponseEntity<MessageDTO>(new MessageDTO("email Notification failed"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	

	
	@Autowired
	MailContentBuilder mailcontentbuilder;
	private  void sendEmail(EmailDTO emailobj) throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(from);
			helper.setTo(emailobj.getTo());
			helper.setSubject(emailobj.getSubject());
			String content = mailcontentbuilder.build();
			helper.setText(content, true);
		 javaMailSender.send(mimeMessage);
	}
	
}
