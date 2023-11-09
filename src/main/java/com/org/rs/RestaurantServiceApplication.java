package com.org.rs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}

}
