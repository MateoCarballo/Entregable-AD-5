# Microservicio de Comentarios - Endpoints GraphQL

## Base URL y GraphiQL
```
http://localhost:8080/comentarios
```

## Queries

### 1. Listar Comentarios de Hotel
```graphql
query {
  listarComentariosHotel(
    nombreHotel: "Hotel A"
    usuario: "Juan Pérez"
    contrasena: "clave123"
  ) {
    id
    usuarioId
    hotelId
    reservaId
    puntuacion
    comentario
    fechaCreacion
  }
}
```

### 2. Listar Comentarios de Usuario
```graphql
query {
  listarComentariosUsuario(
    usuario: "Juan Pérez"
    contrasena: "clave123"
  ) {
    id
    hotelId
    reservaId
    puntuacion
    comentario
    fechaCreacion
  }
}
```

### 3. Mostrar Comentario de Usuario en Reserva
```graphql
query {
  mostrarComentarioUsuarioReserva(
    reservaId: 1
    usuario: "Juan Pérez"
    contrasena: "clave123"
  ) {
    id
    puntuacion
    comentario
    fechaCreacion
  }
}
```

### 4. Obtener Puntuación Media de Hotel
```graphql
query {
  puntuacionMediaHotel(
    nombreHotel: "Hotel A"
    usuario: "Juan Pérez"
    contrasena: "clave123"
  )
}
```

### 5. Obtener Puntuaciones Medias de Usuario
```graphql
query {
  puntuacionesMediasUsuario(
    usuario: "Juan Pérez"
    contrasena: "clave123"
  )
}
```

## Mutations

### 1. Crear Comentario
```graphql
mutation {
  crearComentario(input: {
    nombreHotel: "Hotel A"
    reservaId: 1
    puntuacion: 4.5
    comentario: "Excelente servicio"
    usuario: "Juan Pérez"
    contrasena: "clave123"
  }) {
    id
    puntuacion
    comentario
    fechaCreacion
  }
}
```

### 2. Eliminar Todos los Comentarios
```graphql
mutation {
  eliminarComentarios
}
```

### 3. Eliminar Comentario de Usuario
```graphql
mutation {
  eliminarComentarioDeUsuario(
    id: "comentario_id"
    usuario: "Juan Pérez"
    contrasena: "clave123"
  )
}
```

## Notas
- Todos los endpoints requieren autenticación (usuario y contraseña)
- Las respuestas GraphQL pueden ser personalizadas seleccionando los campos específicos que se necesiten
- Los IDs de comentarios son strings generados por MongoDB
- Las fechas se devuelven en formato ISO 8601 