package com.ig.ecommsolutions.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ig.ecommsolutions.product.domain.Notification;
import com.ig.ecommsolutions.product.service.NotificationService;

@RestController()
@RequestMapping("/notificationService")
public class NotificationController {

	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private NotificationService service;

	@RequestMapping(value = "/notifications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllNotification() {
		log.info("Get all notification...");
		return service.findAllNotification();
	}

	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getNotificationByID(@PathVariable("id") String id) {
		log.info("Get notification by id...");
		return service.findNotificationById(id);
	}

	@RequestMapping(value = "/notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNotification(@RequestBody Notification notification) {
		log.info("Create notification...");
		return service.addNotification(notification);
	}

	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateNotification(@PathVariable("id") String id, @RequestBody Notification notification) {
		log.info("Update notification...");
		return service.updateNotification(id, notification);
	}

	@RequestMapping(value = "/notifications/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteNotification(@PathVariable("id") String id) {
		log.info("Delete notification...");
		return service.deleteNotification(id);
	}

}
