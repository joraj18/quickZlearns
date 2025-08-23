package com.jo.quickZlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuickZlearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickZlearnApplication.class, args);
	}

}
