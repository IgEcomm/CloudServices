/**
 * 
 */
package com.ig.ecommsolution.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Yashpal.Singh
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class IGEEurekaServer {

	public static void main(String[] args) {
		SpringApplication.run(IGEEurekaServer.class, args);
	}

}
