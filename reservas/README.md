# Microservicio de Reservas - Endpoints

## Base URL
```
http://localhost:8080/reservas
```

## Endpoints de Habitaciones

### 1. Crear Habitación
- **URL**: `/habitacion`
- **Método**: `POST`
- **Body**:
```json
{
  "numeroHabitacion": 101,
  "tipo": "Individual",
  "precio": 75.00,
  "idHotel": 1,
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"La habitación fue creada con exito"
```

### 2. Actualizar Habitación
- **URL**: `/habitacion`
- **Método**: `PATCH`
- **Body**:
```json
{
  "id": 1,
  "numeroHabitacion": 101,
  "tipo": "Doble",
  "precio": 120.00,
  "idHotel": 1,
  "disponible": true,
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"Habitacion actualizada con éxito!"
```
### 3. Eliminar Habitación
- **URL**: `/habitacion/{id}`
- **Método**: `DELETE`
- **Parámetros**: 
  - `id` en la URL
  - `usuario` y `contrasena` en body
- **Body**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "clave123"
}
```

- **Respuesta**: 
```java
"Habitacion eliminada con éxito!"
```

## Endpoints de Hoteles

### 1. Crear Hotel
- **URL**: `/hotel`
- **Método**: `POST`
- **Body**:
```json
{
  "nombre": "Hotel Z",
  "direccion": "Calle Principal 123",
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"Hotel creado con exito!"
```

### 2. Actualizar Hotel
- **URL**: `/hotel`
- **Método**: `PATCH`
- **Body**:
```json
{
  "id": 1,
  "nombre": "Hotel A Actualizado",
  "direccion": "Nueva Dirección 456",
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"Hotel actualizado con exito!"
```

### 3. Eliminar Hotel
- **URL**: `/hotel/{id}`
- **Método**: `DELETE`
- **Parámetros**: 
  - `id` en la URL
  - `usuario` y `contrasena` en body
- **Body**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"Hotel eliminado con exito!"
```

### 4. Obtener ID de Hotel
- **URL**: `/hotel/id/{nombre}`
- **Método**: `POST`
- **Parámetros**: `nombre` en la URL
- **Body**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
2 // El id correspondiente al nombre de la url
```

### 5. Obtener Nombre de Hotel
- **URL**: `/hotel/nombre`
- **Método**: `POST`
- **Parámetros**: `id` en la URL
- **Body**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"El hotel con id 2 se llama Hotel B" 
```

## Endpoints de Reservas

### 1. Crear Reserva
- **URL**: `/reserva`
- **Método**: `POST`
- **Body**:
```json
{
  "fecha_inicio": "2024-03-15",
  "fecha_fin": "2024-03-20",
  "habitacion_id": 1,
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
- **Respuesta**: 
```java
"Reserva creado con exito!"
```

### 2. Cambiar Estado de Reserva
- **URL**: `/`
- **Método**: `PATCH`
- **Body**:
```json
{
  "reserva_id": 1,
  "estado": "Confirmada",
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"El estado de la reserva ha cambiado de <estado anterior> a <nuevo estado>" // Dentro de los permitidos por la database
```

### 3. Listar Reservas de Usuario
- **URL**: `/reserva`
- **Método**: `GET`
- **Body**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
  Devuelve un objeto json con todas las reservas del usuario que introducimos, validando contra usuarios credenciales y obteniendo el id.

  ```json
  [
    {
        "habitacionId": 1,
        "fechaInicio": "2024-02-15",
        "fechaFin": "2024-02-20"
    },
    {
        "habitacionId": 4,
        "fechaInicio": "2024-05-15",
        "fechaFin": "2024-05-20"
    },
    {
        "habitacionId": 1,
        "fechaInicio": "2025-06-01",
        "fechaFin": "2025-06-05"
    },
    {
        "habitacionId": 1,
        "fechaInicio": "2025-06-10",
        "fechaFin": "2025-06-15"
    },
    {
        "habitacionId": 1,
        "fechaInicio": "2024-03-15",
        "fechaFin": "2024-03-20"
    }
  ]
  ```

### 4. Listar Reservas por Estado
- **URL**: `/reserva/{estado}`
- **Método**: `GET`
- **Parámetros**: 
  - `estado` en la URL
- **Body**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: 
```json
[
    {
        "reservaId": 1,
        "usuarioId": 1,
        "fechaInicio": "2024-02-15",
        "fechaFin": "2024-02-20",
        "estado": "Confirmada"
    },
    {
        "reservaId": 3,
        "usuarioId": 3,
        "fechaInicio": "2024-04-01",
        "fechaFin": "2024-04-05",
        "estado": "Confirmada"
    },
    {
        "reservaId": 5,
        "usuarioId": 2,
        "fechaInicio": "2024-06-01",
        "fechaFin": "2024-06-05",
        "estado": "Confirmada"
    },
    {
        "reservaId": 6,
        "usuarioId": 1,
        "fechaInicio": "2025-06-01",
        "fechaFin": "2025-06-05",
        "estado": "Confirmada"
    },
    {
        "reservaId": 7,
        "usuarioId": 1,
        "fechaInicio": "2025-06-10",
        "fechaFin": "2025-06-15",
        "estado": "Confirmada"
    }
]
```

### 5. Comprobar Reserva
- **URL**: `/check`
- **Método**: `GET`
- **Parámetros Query**: 
  - `idUsuario`
  - `idHotel`
  - `idReserva`
- **Respuesta**: 
```java
true/false
```