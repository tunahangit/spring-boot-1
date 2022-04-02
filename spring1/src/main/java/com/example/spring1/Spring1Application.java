package com.example.spring1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//exclude= {SecurityAutoConfiguration.class}
@SpringBootApplication()
public class Spring1Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Spring1Application.class, args);
	}

}
