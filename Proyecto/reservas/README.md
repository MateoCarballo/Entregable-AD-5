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
- **Respuesta**: String con el resultado de la operación

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
- **Respuesta**: String con el resultado de la operación

### 3. Eliminar Habitación
- **URL**: `/habitacion/{id}`
- **Método**: `DELETE`
- **Parámetros**: 
  - `id` en la URL
  - `usuario` y `contrasena` en headers
- **Respuesta**: String con el resultado de la operación

## Endpoints de Hoteles

### 1. Crear Hotel
- **URL**: `/hotel`
- **Método**: `POST`
- **Body**:
```json
{
  "nombre": "Hotel A",
  "direccion": "Calle Principal 123",
  "usuario": "Juan Pérez",
  "contrasena": "clave123"
}
```
- **Respuesta**: String con el resultado de la operación

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
- **Respuesta**: String con el resultado de la operación

### 3. Eliminar Hotel
- **URL**: `/hotel/{id}`
- **Método**: `DELETE`
- **Parámetros**: 
  - `id` en la URL
  - `usuario` y `contrasena` en headers
- **Respuesta**: String con el resultado de la operación

### 4. Obtener ID de Hotel
- **URL**: `/hotel/id`
- **Método**: `POST`
- **Parámetros**: `nombre` en la URL
- **Respuesta**: String con el ID del hotel

### 5. Obtener Nombre de Hotel
- **URL**: `/hotel/nombre`
- **Método**: `POST`
- **Parámetros**: `id` en la URL
- **Respuesta**: String con el nombre del hotel

## Endpoints de Reservas

### 1. Crear Reserva
- **URL**: `/`
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
- **Respuesta**: String con el resultado de la operación

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
- **Respuesta**: String con el resultado de la operación

### 3. Listar Reservas de Usuario
- **URL**: `/`
- **Método**: `GET`
- **Headers**:
  - `usuario`
  - `contrasena`
- **Respuesta**: Lista de reservas del usuario

### 4. Listar Reservas por Estado
- **URL**: `/?estado={estado}`
- **Método**: `GET`
- **Parámetros**: 
  - `estado` en la URL
  - `usuario` y `contrasena` en headers
- **Respuesta**: Lista de reservas con el estado especificado

### 5. Comprobar Reserva
- **URL**: `/check`
- **Método**: `GET`
- **Parámetros Query**: 
  - `idUsuario`
  - `idHotel`
  - `idReserva`
- **Respuesta**: Boolean indicando si existe la reserva 