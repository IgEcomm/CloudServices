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
public class WishlistIntegrationService {

	private Log log = LogFactory.getLog(WishlistIntegrationService.class);

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubWishlist", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE") })
	@Cacheable("wishlist")
	public String getWishlist(final String userId) {		
		String wishlist = restTemplate.getForObject("http://ige-product-services/wishlistService/wishLists?userId={userId}",
				String.class, userId);		
		return wishlist;
	}

	@SuppressWarnings("unused")
	private String stubWishlist(final String userId) {
		return "Invalid userid to fetch wishlist...";
	}
	
}
