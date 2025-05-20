package com.mateo._1.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Aquí defines las rutas hacia los microservicios usando lb://nombre-aplicacion (de Eureka)
				.route("usuarios", r -> r.path("/usuarios/**")
						.uri("lb://usuarios"))
				.route("reservas", r -> r.path("/reservas/**")
						.uri("lb://reservas"))
				// Añade más rutas según tus microservicios
				.build();
	}
}
