package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.ContactUs;

public interface ContactUsService {

	public ResponseEntity<?> addContactUs(ContactUs contactUs);
	public ResponseEntity<?> updateContactUs(String id, ContactUs contactUs);
	public ResponseEntity<?> deleteContactUs(String id);
	public ResponseEntity<?> findContactUsById(String id);
	public ResponseEntity<?> findAllContactUs();
	
}
