package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.demo")
@SpringBootApplication
public class FeignClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignClientDemoApplication.class, args);
	}

}
