# Entregable-AD-5
## Sistema de Reservas y Comentarios - Microservicios

Proyecto final, entregable durante la tercera evaluacion. 

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

Este proyecto implementa todos los módulos requeridos aunque el ultimo de ellos "comentarios" no tiene implementada ninguna de las funcionalidades pedidas. Solo de puedo obtener todos los comentarios contenidos en la coleccion de Mongo. Tampoco funciona el entorno web de graphql para realizar consultas. Para poder probar la conexion contra Mongo he tenido que usar postman. 

Parametros introducidos en postman para obtener todos los comentarios de la coleccion:

- **url (buena)**
```json
"http://localhost:8503/graphql"
```

- **Body**
```json
{
    "query": "query { getComentarios {id, usuarioId,hotelId,reservaId,puntuacion,comentario,fechaCreacion} }"
}
```

- **Respuesta**
```json
{
    "data": {
        "getComentarios": [
            {
                "id": "682c706e759e9f2f071d3c5b",
                "usuarioId": 4,
                "hotelId": 1,
                "reservaId": 1,
                "puntuacion": 6.5,
                "comentario": "Excelente servicio",
                "fechaCreacion": "2024-01-01T12:00"
            },
            {
                "id": "682c706e759e9f2f071d3c5c",
                "usuarioId": 1,
                "hotelId": 3,
                "reservaId": 4,
                "puntuacion": 5.0,
                "comentario": "Excelente servicio",
                "fechaCreacion": "2024-01-01T12:00"
            },
            {
                "id": "682c706e759e9f2f071d3c5d",
                "usuarioId": 2,
                "hotelId": 1,
                "reservaId": 2,
                "puntuacion": 4.0,
                "comentario": "Buena experiencia",
                "fechaCreacion": "2024-01-02T12:30"
            },
            {
                "id": "682c706e759e9f2f071d3c5e",
                "usuarioId": 3,
                "hotelId": 2,
                "reservaId": 3,
                "puntuacion": 3.0,
                "comentario": "Podría mejorar",
                "fechaCreacion": "2024-01-03T13:00"
            }
        ]
    }
}
```
sin embargo al usar esta otra url con el mismo cuerpo la respuesta es otra.
- **url (rota)**
```json
"http://localhost:8503/graphql"
```
- **Respuesta url rota**
```json
{
    "timestamp": "2025-05-23T10:15:24.719+00:00",
    "path": "/graphql",
    "status": 404,
    "error": "Not Found",
    "requestId": "e0b379ab-7"
}
```
Ademas de que en nignun caso funciona a traves del puerto 8080 como el resto de los microservicios(usuarios y reservas).

## Requisitos de Ejecución

1. Java 17 o superior
2. Maven
3. MySQL
4. MongoDB

## Instrucciones de Ejecución

1. Iniciar el servidor Eureka
2. Iniciar los servicios de Usuarios, Reservas y Comentarios 
3. Iniciar el API Gateway

Los servicios se registrarán automáticamente en Eureka y estarán disponibles a través del API Gateway(salvo comentarios). 