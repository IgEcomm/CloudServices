package com.ig.ecommsolutions.notification.controller;


import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ig.ecommsolutions.notification.dto.EmailDTO;
import com.ig.ecommsolutions.notification.dto.PushNotificationDTO;
import com.ig.ecommsolutions.notification.service.NotificationService;


@RestController
@RequestMapping("/notification")
public class NotificationController {
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	@Autowired
	private NotificationService service;
	
	@RequestMapping(value="/pushNotification", method=RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> pushNotification(@RequestBody  PushNotificationDTO notification ) {
		log.info("pushNotification	"+ notification);
		return service.pushNotification(notification);  
	}
	
	@RequestMapping(value="/emailNotification" , method=RequestMethod.POST ,consumes=MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<?> emailNotification(@RequestBody EmailDTO emailobj ){
		 log.info("emailNotification	"+emailobj.getTo());
		 return  service.emailNotification(emailobj); 
	}
	
	
 
}
