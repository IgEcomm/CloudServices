package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.Notification;

public interface NotificationService {

	public ResponseEntity<?> addNotification(Notification notification);
	public ResponseEntity<?> updateNotification(String id, Notification notification);
	public ResponseEntity<?> deleteNotification(String id);
	public ResponseEntity<?> findNotificationById(String id);
	public ResponseEntity<?> findAllNotification();
	
}
