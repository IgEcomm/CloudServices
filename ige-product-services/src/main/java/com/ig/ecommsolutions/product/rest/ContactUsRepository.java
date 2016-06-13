package com.ig.ecommsolutions.product.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolutions.product.domain.ContactUs;

public interface ContactUsRepository extends MongoRepository<ContactUs, String>{

}
