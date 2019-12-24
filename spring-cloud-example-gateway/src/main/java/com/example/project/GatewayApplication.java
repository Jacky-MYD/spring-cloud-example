/**
 * @authour Jacky
 * @data Dec 19, 2019
 */
package com.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Jacky
 *
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(GatewayApplication.class, args);
	}

	
}
