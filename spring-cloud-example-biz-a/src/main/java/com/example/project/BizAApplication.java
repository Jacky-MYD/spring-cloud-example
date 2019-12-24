package com.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@ComponentScan({"com.example.project.*"})
public class BizAApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(BizAApplication.class, args);
	}
}
