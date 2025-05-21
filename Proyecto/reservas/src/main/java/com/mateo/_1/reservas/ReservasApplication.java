package com.mateo._1.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ReservasApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservasApplication.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

/*
@SpringBootApplication
@EnableDiscoveryClient
public class ReservasApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReservasApplication.class, args);
	}
}
 */
