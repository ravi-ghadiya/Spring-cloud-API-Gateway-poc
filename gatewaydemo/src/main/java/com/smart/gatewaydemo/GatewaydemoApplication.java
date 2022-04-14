package com.smart.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.smart")
public class GatewaydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewaydemoApplication.class, args);
	}

}
