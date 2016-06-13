package com.ig.ecommsolutions.product.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.product.domain.ContactUs;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.rest.ContactUsRepository;
import com.ig.ecommsolutions.product.service.ContactUsService;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	private static final Logger log = LoggerFactory.getLogger(ContactUsServiceImpl.class);

	@Autowired
	private ContactUsRepository repository;

	@Override
	public ResponseEntity<?> addContactUs(ContactUs contactUs) {

		try {
			ContactUs persistedContactUs = repository.save(contactUs);
			return new ResponseEntity<>(persistedContactUs, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Failed to create contactUs: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateContactUs(String id, ContactUs contactUs) {

		try {
			ContactUs existingContactUs = repository.findOne(id);

			if (existingContactUs == null) {
				return new ResponseEntity<>(new MessageDTO("ContactUs not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			if (contactUs.getSubject() != null)
				existingContactUs.setSubject(contactUs.getSubject());
			if (contactUs.getMessage() != null)
				existingContactUs.setMessage(contactUs.getMessage());
			if (contactUs.getUserId() != null)
				existingContactUs.setUserId(contactUs.getUserId());
			if (contactUs.getType() != null)
				existingContactUs.setType(contactUs.getType());

			ContactUs persistedContactUs = repository.save(existingContactUs);
			return new ResponseEntity<>(persistedContactUs, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to update contactUs: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteContactUs(String id) {

		try {
			ContactUs existingContactUs = repository.findOne(id);

			if (existingContactUs == null) {
				return new ResponseEntity<>(new MessageDTO("ContactUs not found with id: " + id), HttpStatus.NOT_FOUND);
			}
			repository.delete(id);
			return new ResponseEntity<>(new MessageDTO("ContactUs has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete contactUs: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findContactUsById(String id) {

		try {
			ContactUs existingContactUs = repository.findOne(id);

			if (existingContactUs == null) {
				return new ResponseEntity<>(new MessageDTO("ContactUs not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(existingContactUs, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find contactUs by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllContactUs() {

		try {

			List<ContactUs> contactUs = repository.findAll();
			if (contactUs == null || contactUs.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}
			return new ResponseEntity<>(contactUs, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find contactUs: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
