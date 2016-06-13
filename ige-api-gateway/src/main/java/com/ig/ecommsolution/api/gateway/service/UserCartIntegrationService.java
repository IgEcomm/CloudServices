/**
 * 
 */
package com.ig.ecommsolution.api.gateway.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author Yashpal.Singh
 *
 */
@Service
public class UserCartIntegrationService {

	private Log log = LogFactory.getLog(UserCartIntegrationService.class);

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubUserCart", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE") })
	@Cacheable("carts")
	public String getUserCart(final String userId) {		
		String carts = restTemplate.getForObject("http://ige-product-services/cartService/carts?userId={userId}",
				String.class, userId);		
		return carts;
	}

	@SuppressWarnings("unused")
	private String stubUserCart(final String userId) {
		return "Invalid userid to fetch carts...";
	}

}
