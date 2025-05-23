package com.mateo._1.api_gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	private static final Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class); // <--- Añade esta línea

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		logger.info("Configurando rutas del API Gateway...");
		return builder.routes()
				.route("usuarios", r -> r.path("/usuarios/**")
						.uri("lb://usuarios"))
				.route("reservas", r -> r.path("/reservas/**")
						.uri("lb://reservas"))
				.route("comentarios", r -> r.path("/comentarios/**")
						.uri("lb://comentarios"))
				.build();
	}
}
