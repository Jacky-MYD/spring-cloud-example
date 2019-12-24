/**
 * @authour Jacky
 * @data Dec 19, 2019
 */
package com.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Jacky
 *
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.example.project.*"})
public class BizBApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(BizBApplication.class, args);
	}

}
