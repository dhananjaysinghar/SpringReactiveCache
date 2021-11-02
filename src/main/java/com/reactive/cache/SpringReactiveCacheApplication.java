package com.reactive.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringReactiveCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveCacheApplication.class, args);
	}

}
