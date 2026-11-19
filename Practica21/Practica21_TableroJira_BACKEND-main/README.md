# Tasks API (Spring Boot Version)

Este proyecto es una API RESTful para gestionar tareas (Tasks), construida con **Spring Boot**. 
Ofrece exactamente los mismos endpoints y comportamientos que la versión anterior (Java EE / Servlet / JPA).

## Requisitos
- **Java 17** (o superior)
- **Maven** (para construir y ejecutar el proyecto)
- **MySQL Server** (por ejemplo, a través de MySQL Workbench)

## Configuración de Base de Datos
La aplicación está configurada para conectarse a una base de datos MySQL local:
- **URL**: `jdbc:mysql://localhost:3306/tasks_db`
- **Usuario**: `Pruebas`
- **Contraseña**: `Pruebas.1234`

**Nota:** La base de datos `tasks_db` se creará automáticamente si no existe (gracias a `createDatabaseIfNotExist=true` en la cadena de conexión), y las tablas también se generarán automáticamente gracias a Hibernate.

## Ejecutar el Proyecto
Para ejecutar el proyecto, simplemente abre una terminal en la raíz del proyecto y ejecuta el siguiente comando de Maven:

```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`.

## Endpoints de la API

El endpoint principal es `/api/task`. La API está habilitada con **CORS**, por lo que puede ser consumida desde cualquier cliente web (frontend) sin bloqueos.

### 1. Obtener todas las tareas
- **URL**: `http://localhost:8080/TasksAPI/api/task`
- **Método**: `GET`
- **Respuesta Exitosa**: `200 OK`
- **Ejemplo de Respuesta**:
```json
[
  {
    "id": 1,
    "text": "Aprender Spring Boot",
    "completed": true
  },
  {
    "id": 2,
    "text": "Crear proyecto API",
    "completed": false
  }
]
```

### 2. Crear una nueva tarea
- **URL**: `http://localhost:8080/TasksAPI/api/task`
- **Método**: `POST`
- **Cuerpo de la Petición** (JSON):
```json
{
  "text": "Nueva tarea a realizar",
  "completed": false
}
```
- **Respuesta Exitosa**: `201 Created`
- **Ejemplo de Respuesta**: Retorna la tarea creada con su `id` asignado.

### 3. Actualizar una tarea existente
- **URL**: `http://localhost:8080/TasksAPI/api/task`
- **Método**: `PUT`
- **Cuerpo de la Petición** (JSON): (Debe incluir el `id` de la tarea a modificar)
```json
{
  "id": 1,
  "text": "Aprender Spring Boot Avanzado",
  "completed": true
}
```
- **Respuesta Exitosa**: `200 OK`
- **Ejemplo de Respuesta**: Retorna la tarea con los datos actualizados.

### 4. Eliminar una tarea
- **URL**: `http://localhost:8080/TasksAPI/api/task?id={ID}`
- **Método**: `DELETE`
- **Ejemplo de URL**: `http://localhost:8080/TasksAPI/api/task?id=1`
- **Respuesta Exitosa**: `204 No Content` (No devuelve cuerpo, solo el estado de éxito).

## Notas para alumnos
- Este proyecto usa `spring-boot-starter-data-jpa` para facilitar las operaciones de base de datos.
- `@RestController` y `@RequestMapping("/api/task")` definen las rutas base.
- `@CrossOrigin(origins = "*")` permite solicitudes desde otros dominios (útil para Single Page Applications).
