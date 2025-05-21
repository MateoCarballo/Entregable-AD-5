package com.example.comentarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ComentariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComentariosApplication.class, args);
    }
} 