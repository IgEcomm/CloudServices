package com.ig.ecommsolutions.product.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolutions.product.domain.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>{

}
