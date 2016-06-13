package com.ig.ecommsolutions.product.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.product.domain.Notification;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.rest.NotificationRepository;
import com.ig.ecommsolutions.product.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationRepository repository;

	@Override
	public ResponseEntity<?> addNotification(Notification notification) {

		try {
			Notification persistedNotification = repository.save(notification);
			return new ResponseEntity<>(persistedNotification, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Failed to create notification: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateNotification(String id, Notification notification) {

		try {
			Notification existingNotification = repository.findOne(id);

			if (existingNotification == null) {
				return new ResponseEntity<>(new MessageDTO("Notification not found with id: " + id),
						HttpStatus.NOT_FOUND);
			}

			if (notification.getSubject() != null)
				existingNotification.setSubject(notification.getSubject());
			if (notification.getMessage() != null)
				existingNotification.setMessage(notification.getMessage());
			if (notification.getUserId() != null)
				existingNotification.setUserId(notification.getUserId());
			if (notification.getType() != null)
				existingNotification.setType(notification.getType());
			if (notification.getStatus() != null)
				existingNotification.setStatus(notification.getStatus());
			if (notification.getStartDate() != null)
				existingNotification.setStartDate(notification.getStartDate());
			if (notification.getEndDate() != null)
				existingNotification.setEndDate(notification.getEndDate());

			Notification persistedNotification = repository.save(existingNotification);
			return new ResponseEntity<>(persistedNotification, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to update notification: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteNotification(String id) {

		try {
			Notification existingNotification = repository.findOne(id);

			if (existingNotification == null) {
				return new ResponseEntity<>(new MessageDTO("Notification not found with id: " + id),
						HttpStatus.NOT_FOUND);
			}
			repository.delete(id);
			return new ResponseEntity<>(new MessageDTO("Notification has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete notification: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findNotificationById(String id) {

		try {
			Notification existingNotification = repository.findOne(id);

			if (existingNotification == null) {
				return new ResponseEntity<>(new MessageDTO("Notification not found with id: " + id),
						HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(existingNotification, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find notification by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllNotification() {

		try {

			List<Notification> notification = repository.findAll();
			if (notification == null || notification.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}
			return new ResponseEntity<>(notification, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find notification: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
