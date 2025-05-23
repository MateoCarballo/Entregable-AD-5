# Microservicio de Usuarios - Endpoints

## Base URL
```
http://localhost:8080/usuarios
```

## Endpoints

### 1. Crear Usuario
- **URL**: `/registrar`
- **Método**: `POST`
- **Body**:
```json
{
    "nombre": "Juan Pérez",
    "correo_electronico": "juan@example.com",
    "direccion": "Calle Principal 123",
    "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
 "Nuevo usuario guardado con id 12" 
 ```

### 2. Actualizar Usuario
- **URL**: `/registrar`
- **Método**: `PUT`
- **Body**:
```json
{
    "id": 1,
    "nombre": "Juan Pérez",
    "correo_electronico": "juan@example.com",
    "direccion": "Nueva Dirección 456",
    "contrasena": "nuevaclave123"
}
```
- **Respuesta**: 
```java
"Usuario modificado con exito"
```

### 3. Eliminar Usuario
- **URL**: `/`
- **Método**: `DELETE`
- **Body**:
```json
{
    "nombre": "Juan Pérez",
    "contrasena": "clave123"
}
```
- **Respuesta**: 
```java
"Usuario eliminado con exito"
```

### 4. Validar Usuario
- **URL**: `/validar`
- **Método**: `POST`
- **Body**:
```json
{
    "nombre": "Juan Pérez",
    "contrasena": "clave123"
}
```
- **Respuesta**: Boolean indicando si es válido

### 5. Obtener Info Usuario por ID
- **URL**: `/info/id/{id}`
- **Método**: `GET`
- **Parámetros**: `id` en la URL
- **Respuesta**: String con el nombre del usuario

### 6. Obtener Info Usuario por Nombre
- **URL**: `/info/nombre/{nombre}`
- **Método**: `GET`
- **Parámetros**: `nombre` en la URL
- **Respuesta**: String con el ID del usuario

### 7. Comprobar si Usuario Existe
- **URL**: `/checkIfExist/{id}`
- **Método**: `GET`
- **Parámetros**: `id` en la URL
- **Respuesta**: Boolean indicando si existe 