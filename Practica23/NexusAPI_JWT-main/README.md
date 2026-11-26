# 🔐 NexusAPI (con JWT)

API REST de una red social básica (usuarios, posts y comentarios) hecha con **Spring Boot 4 + MySQL + JWT**.
Es la versión **segura** de [NexusAPI sin JWT](https://github.com/MartinBernal06/NexusAPI): aquí el usuario **nunca** manda su id, el servidor lo saca del token.

---

## ▶️ Arranque rápido

1. Tener MySQL corriendo. La base `nexus` se crea sola.
2. Poner tus credenciales en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=tu_password
   ```
3. Ejecutar `NexusApplication.java` desde IntelliJ o en terminal:
   ```bash
   .\mvnw.cmd spring-boot:run     # Windows
   ./mvnw spring-boot:run         # macOS / Linux
   ```
4. La API queda en **`http://localhost:8080/api`**.
5. (Opcional) Cargar los [datos de prueba](#-datos-de-prueba). Todos los usuarios tienen contraseña `1234`.

---

## 🔗 Endpoints

Solo **login** y **registro** son públicos. Todo lo demás requiere el header:

```
Authorization: Bearer <token>
```

| Método | Ruta | Body | Qué hace |
|---|---|---|---|
| POST | `/api/auth/register` | `{ "username", "email", "password" }` | Crea una cuenta |
| POST | `/api/auth/login` | `{ "username", "password" }` | Devuelve `{ token, user }` |
| GET | `/api/posts` | — | Todos los posts (feed) |
| GET | `/api/posts/mine` | — | Solo **mis** posts |
| POST | `/api/posts` | `{ "title", "content" }` | Publica a **mi** nombre |
| GET | `/api/comments/post/{postId}` | — | Comentarios de un post |
| POST | `/api/comments` | `{ "postId", "text" }` | Comenta a **mi** nombre |

### Respuesta del login

```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "user": { "id": 1, "username": "carlos_dev", "email": "carlos@nexus.com" }
}
```

El frontend guarda el `token` para las siguientes peticiones y el `user` para mostrar quién inició sesión. No hace falta decodificar el token.

### Respuesta de un post

```json
{
  "id": 8,
  "title": "Mi post",
  "content": "Contenido",
  "createdAt": "2026-11-24T23:09:24.57",
  "user": { "id": 1, "username": "carlos_dev", "email": "carlos@nexus.com" }
}
```

### Errores

| Código | Cuándo |
|---|---|
| 400 | Faltan campos, JSON mal formado o el username ya existe |
| 401 | Sin token, token inválido o expirado, o credenciales incorrectas |
| 404 | El post o la ruta no existen |

---

## 🧠 ¿Cómo sabe el servidor quién soy?

```
Cliente ──Bearer token──► JwtFilter ──valida firma──► request.setAttribute("username", sub)
                                                              │
                                     PostController / CommentController
                                     request.getAttribute("username") → busca al User → guarda
```

1. En el login, `JwtService` genera un token cuyo **subject (`sub`)** es el username, firmado con `jwt.secret`.
2. En cada petición, `JwtFilter` revisa la firma. Si alguien altera el token, la firma no coincide y responde **401**.
3. Si es válido, guarda el username en el request y los controladores lo usan como autor.
4. Aunque el cliente mande `"userId": 999` en el body, se ignora.

| Archivo | Rol |
|---|---|
| `security/JwtFilter.java` | Revisa el token en cada petición, define las rutas públicas y abre CORS |
| `services/JwtService.java` | Genera y valida tokens (librería JJWT) |
| `controllers/AuthController.java` | Registro y login |

---

## 🆚 Diferencias con la versión sin JWT

| Sin JWT | Con JWT |
|---|---|
| `POST /api/users` (registro) | `POST /api/auth/register` |
| No hay login | `POST /api/auth/login` → `{ token, user }` |
| `POST /api/posts` lleva `userId` | El autor sale del token |
| `POST /api/comments` lleva `userId` | El autor sale del token |
| `GET /api/posts/user/{userId}` (los de cualquiera) | `GET /api/posts/mine` (solo los míos) |
| Todo es público | Todo pide token, menos login y registro |

---

## 🌱 Datos de prueba

Ejecútalos en MySQL **después** del primer arranque, cuando Hibernate ya creó las tablas.

```sql
INSERT INTO users (username, email, password) VALUES
('carlos_dev',   'carlos@nexus.com', '1234'),
('maria_ux',     'maria@nexus.com',  '1234'),
('juan_backend', 'juan@nexus.com',   '1234'),
('laura_design', 'laura@nexus.com',  '1234'),
('pedro_qa',     'pedro@nexus.com',  '1234');

INSERT INTO posts (title, content, created_at, user_id) VALUES
('Bienvenidos a Nexus', 'Esta es la primera publicación de la plataforma. ¡Esperamos que disfruten la experiencia!', NOW(), 1),
('Tips de UI/UX para principiantes', 'Hoy les comparto 5 tips esenciales para mejorar la experiencia de usuario en sus aplicaciones...', NOW(), 2),
('Spring Boot con MySQL paso a paso', 'En este post explico cómo configurar Spring Boot con una base de datos MySQL correctamente.', NOW(), 3),
('¿Qué es el diseño minimalista?', 'El diseño minimalista se basa en la simplicidad, eliminando lo innecesario para destacar lo esencial.', NOW(), 4),
('Cómo hacer pruebas unitarias en Java', 'Las pruebas unitarias son fundamentales para garantizar la calidad del software. Aquí un ejemplo con JUnit...', NOW(), 5),
('React vs Angular en 2025', 'Comparativa detallada de dos de los frameworks más populares del frontend actual.', NOW(), 1),
('Bases de datos NoSQL: ¿cuándo usarlas?', 'MongoDB, Redis, Cassandra... ¿cuál elegir y cuándo? Guía práctica para developers.', NOW(), 3);

INSERT INTO comments (text, created_at, post_id, user_id) VALUES
('¡Excelente iniciativa! Esperando más contenido.', NOW(), 1, 2),
('Muy buena bienvenida, el equipo se ve muy profesional.', NOW(), 1, 3),
('Justo lo que necesitaba, gracias por los tips!', NOW(), 2, 1),
('Muy claro el tutorial, me ayudó a resolver mi error de conexión.', NOW(), 3, 2),
('Me encanta el enfoque minimalista, menos es más!', NOW(), 4, 1),
('JUnit es genial, ¿conoces Mockito también?', NOW(), 5, 1),
('Yo sigo prefiriendo React por su ecosistema.', NOW(), 6, 4),
('MongoDB para datos no estructurados es una maravilla.', NOW(), 7, 2);
```

---

## 🧪 Prueba rápida en Postman

1. `POST /api/auth/login` con `{ "username": "carlos_dev", "password": "1234" }` → copia el `token`.
2. Pega el token en [jwt.io](https://jwt.io) y ubica el campo `sub`.
3. En Postman ve a **Authorization → Bearer Token** y pega el token.
4. `POST /api/posts` con `{ "title": "Hola", "content": "Desde Postman" }` → el autor es `carlos_dev`.
5. `GET /api/posts/mine` → solo aparecen los posts de `carlos_dev`.
6. Quita el token y repite: debe responder **401**.

> ⚠️ Proyecto didáctico: las contraseñas se guardan en texto plano y el CORS está abierto (`*`). En producción se usa BCrypt y se limitan los orígenes.
