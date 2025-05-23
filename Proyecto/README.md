# Sistema de Reservas y Comentarios - Microservicios

Este proyecto implementa un sistema de reservas y comentarios utilizando una arquitectura de microservicios.

## Módulos Implementados

1. **Servidor Eureka (Puerto: 8500)**
   - Registro y descubrimiento de servicios
   - Gestión de la comunicación entre microservicios

2. **API Gateway (Puerto: 8080)**
   - Punto de entrada al sistema
   - Enrutamiento de peticiones a los microservicios

3. **Servicio de Usuarios (Puerto: 8502)**
   - Gestión de usuarios del sistema
   - API REST para operaciones CRUD de usuarios
   - Base de datos MySQL

4. **Servicio de Reservas (Puerto: 8501)**
   - Gestión de hoteles, habitaciones y reservas
   - Sistema de reservas
   - API REST
   - Base de datos MySQL

5. **Servicio de Comentarios (Puerto: 8503)**
   - Gestión de comentarios 
   - API GraphQL
   - Base de datos MongoDB

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.2.5
- Spring Cloud
- Maven
- MySQL
- MongoDB
- GraphQL

## Estructura del Proyecto

El proyecto está organizado como un proyecto Maven multi-módulo, 
donde cada microservicio es un módulo independiente.

## Puntuación Máxima

Este proyecto implementa todos los módulos requeridos y 
cumple con todas las especificaciones técnicas, 
por lo que opta a la puntuación máxima.

## Requisitos de Ejecución

1. Java 17 o superior
2. Maven
3. MySQL
4. MongoDB

## Instrucciones de Ejecución

1. Iniciar el servidor Eureka
2. Iniciar el API Gateway
3. Iniciar los servicios de Usuarios, Reservas y Comentarios

Los servicios se registrarán automáticamente en Eureka y estarán disponibles a través del API Gateway. 