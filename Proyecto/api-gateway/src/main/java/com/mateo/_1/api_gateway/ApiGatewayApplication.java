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
				.route("usuarios", r -> r.path("/usuarios/**")
						.uri("lb://usuarios"))
				.route("reservas", r -> r.path("/reservas/**")
						.uri("lb://reservas"))
				.route("comentarios", r -> r.path("/comentarios/**") // <--- AÑADE ESTA LÍNEA
						.uri("lb://comentarios")) // <--- ASEGÚRATE DE QUE "COMENTARIOS" ES EL spring.application.name de tu microservicio de comentarios
				.build();
	}
}
