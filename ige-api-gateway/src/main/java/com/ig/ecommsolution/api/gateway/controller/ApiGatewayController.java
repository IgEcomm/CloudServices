/**
 * 
 */
package com.ig.ecommsolution.api.gateway.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ig.ecommsolution.api.gateway.service.OrderIntegrationService;
import com.ig.ecommsolution.api.gateway.service.UserCartIntegrationService;
import com.ig.ecommsolution.api.gateway.service.WishlistIntegrationService;

/**
 * @author Yashpal.Singh
 *
 */
@RestController
@RequestMapping("/gateway")
public class ApiGatewayController {

	@Autowired
	private UserCartIntegrationService cartService;

	@Autowired
	private WishlistIntegrationService wishlistService;

	@Autowired
	private OrderIntegrationService orderService;

	private Log log = LogFactory.getLog(ApiGatewayController.class);

	@RequestMapping(value = "/carts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String findCarts(@RequestParam("userId") String userId) {
		log.info(String.format("Loading carts by userid: %s", userId));
		return cartService.getUserCart(userId);
	}

	@RequestMapping(value = "/wishlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String findWishlist(@RequestParam("userId") String userId) {
		log.info(String.format("Loading wishlist by userid: %s", userId));
		return wishlistService.getWishlist(userId);
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String findOrders(@RequestParam("userId") String userId) {
		log.info(String.format("Loading orders by userid: %s", userId));
		return orderService.getOrders(userId);
	}
	
}
