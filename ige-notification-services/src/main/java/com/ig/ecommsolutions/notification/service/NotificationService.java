package com.ig.ecommsolutions.notification.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.notification.dto.EmailDTO;
import com.ig.ecommsolutions.notification.dto.PushNotificationDTO;

public interface NotificationService {

	public ResponseEntity<?> pushNotification(PushNotificationDTO notification);
	public ResponseEntity<?> emailNotification(EmailDTO emailobj) ;
}
