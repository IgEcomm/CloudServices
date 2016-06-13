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
public class OrderIntegrationService {

	private Log log = LogFactory.getLog(OrderIntegrationService.class);

	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "stubOrders", commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE") })
	@Cacheable("orders")
	public String getOrders(final String userId) {		
		String orders = restTemplate.getForObject("http://ige-product-services/orderService/orders?userId={userId}",
				String.class, userId);		
		return orders;
	}

	@SuppressWarnings("unused")
	private String stubOrders(final String userId) {
		return "Invalid userid to fetch orders...";
	}
	
}
