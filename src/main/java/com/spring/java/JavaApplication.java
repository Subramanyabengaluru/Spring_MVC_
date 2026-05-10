package com.spring.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavaApplication {

	public static void main(String[] args) {
		System.out.println("welcome to Spring learning");
		SpringApplication.run(JavaApplication.class, args);
	}

}
