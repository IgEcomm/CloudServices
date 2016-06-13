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

import com.ig.ecommsolutions.product.domain.ContactUs;
import com.ig.ecommsolutions.product.service.ContactUsService;

@RestController()
@RequestMapping("/contactUsService")
public class ContactUsController {

	private static final Logger log = LoggerFactory.getLogger(ContactUsController.class);

	@Autowired
	private ContactUsService service;

	@RequestMapping(value = "/contactUs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllContactUs() {
		log.info("Get all contactUs...");
		return service.findAllContactUs();
	}

	@RequestMapping(value = "/contactUs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getContactUsByID(@PathVariable("id") String id) {
		log.info("Get contactUs by id...");
		return service.findContactUsById(id);
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addContactUs(@RequestBody ContactUs contactUs) {
		log.info("Create contactUs...");
		return service.addContactUs(contactUs);
	}

	@RequestMapping(value = "/contactUs/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContactUs(@PathVariable("id") String id, @RequestBody ContactUs contactUs) {
		log.info("Update contactUs...");
		return service.updateContactUs(id, contactUs);
	}

	@RequestMapping(value = "/contactUs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteContactUs(@PathVariable("id") String id) {
		log.info("Delete contactUs...");
		return service.deleteContactUs(id);
	}

}
