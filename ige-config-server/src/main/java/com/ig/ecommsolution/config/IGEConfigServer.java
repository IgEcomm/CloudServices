/**
 * 
 */
package com.ig.ecommsolution.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author yashpal.singh
 *
 */
@SpringBootApplication
@EnableConfigServer
public class IGEConfigServer {

	public static void main(String[] args) {
		SpringApplication.run(IGEConfigServer.class, args);
	}

}
