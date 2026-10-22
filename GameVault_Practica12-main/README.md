# Práctica 12 · GameVault: Sesiones, Interceptores y Propiedad de Recursos

> ## 🛡️ "El cliente/usuario siempre miente. No se confía en él."
> Todo lo que llega del navegador (URL, formularios, IDs, campos ocultos) se puede modificar.
> **Siempre valida en el servidor que el recurso le pertenezca a quien lo pide.**

## ¿De qué trata este repo?

GameVault es un catálogo de videojuegos donde cada usuario administra **solo sus juegos**.
El objetivo es la **seguridad**: proteger rutas con sesión usando un **Interceptor** y validar la **propiedad** de cada recurso.

| Tema | Dónde verlo |
|---|---|
| Login / registro con BCrypt | `AuthController`, `UsuarioService`, `SecurityConfig` |
| Guardia de rutas por sesión | `AuthInterceptor`, `WebConfig` |
| Validar que el juego es tuyo | `GameController` (editar / eliminar) |
| Relación 1:N Usuario → Juegos | `Usuario`, `Juego` |
| Layout y fragmentos Thymeleaf | `layout/base.html`, `fragments-demo.html` |

## Cómo correrlo

1. Ten MySQL encendido y ajusta usuario/contraseña en `application.properties`.
2. (Opcional) Pon tus credenciales de Cloudinary para subir portadas.
3. Ejecuta `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`).
4. Abre `http://localhost:8080`.

---

## 🔐 Reglas de seguridad que debes cumplir

- **Nunca** uses un ID que venga del formulario para saber quién es el usuario; sácalo de la **sesión**.
- Antes de **ver, editar o eliminar**, compara el dueño del recurso con el usuario en sesión.
- Protege **GET y POST**: si proteges `/juegos/editar/1` pero no el `POST`, alguien lo puede llamar con Postman.
- Ocultar un botón en el HTML **no es seguridad**; la validación va en el servidor.
- Las contraseñas se guardan con `passwordEncoder.encode()` y se comparan con `matches()`, nunca en texto plano.

---

## 🚧 Validar rutas por sesión con un Interceptor

El interceptor se ejecuta **antes** del controlador. Si no hay sesión, corta la petición.

```java
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        HttpSession session = req.getSession(false); // false: no crea una sesión nueva
        if (session == null || session.getAttribute("usuario_id") == null) {
            res.sendRedirect("/login");
            return false; // detiene la petición
        }
        return true; // deja pasar al controlador
    }
}
```

Regístralo y define qué rutas cuida:

```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor)
            .addPathPatterns("/mis-juegos", "/juegos/nuevo", "/juegos/editar/**", "/juegos/eliminar/**")
            .excludePathPatterns("/login", "/registro", "/styles/**", "/error");
}
```

**Tips:**
- Si usas `"/**"`, excluye `/login`, `/registro`, estáticos y `/error`, o tendrás un **loop de redirecciones**.
- Revisa que **todas** las rutas que crean/modifican datos estén protegidas (incluido el `POST`).
- El interceptor responde *"¿estás logueado?"*; el controlador debe responder *"¿esto es tuyo?"*.

## ✅ Validar que el recurso te pertenece

```java
Juego juego = juegoService.obtenerJuegoPorId(id);
Usuario logueado = usuarioService.obtenerUsuarioLogueado();

if (juego == null || !juego.getUsuario().getId().equals(logueado.getId())) {
    throw new ResponseStatusException(HttpStatus.FORBIDDEN); // o redirige
}
```

Mejor aún, deja que la BD filtre por dueño: `Optional<Juego> findByIdAndUsuarioId(Long id, Long usuarioId);`

---

## ⚠️ Página de error personalizada

Spring Boot busca automáticamente estas plantillas:

```
src/main/resources/templates/
├── error.html          ← cualquier error
└── error/
    ├── 403.html        ← prohibido
    ├── 404.html        ← no existe
    └── 500.html        ← error del servidor
```

Ejemplo de `templates/error.html`:

```html
<!DOCTYPE html>
<html lang="es" xmlns:th="http://www.thymeleaf.org">
<head><meta charset="UTF-8"><title>Error</title></head>
<body>
    <h1 th:text="${status}">500</h1>
    <p th:text="${error}">Algo salió mal</p>
    <p>Ruta: <span th:text="${path}">/</span></p>
    <a th:href="@{/juegos}">Volver al inicio</a>
</body>
</html>
```

En `application.properties`:

```properties
server.error.whitelabel.enabled=false
server.error.include-stacktrace=never
```

**Tips:**
- Nunca muestres el stacktrace al usuario: le das pistas a un atacante.
- Si usas un interceptor con `"/**"`, excluye `/error` o la página de error también será bloqueada.
- Para lanzar un 403/404 desde el controlador: `throw new ResponseStatusException(HttpStatus.NOT_FOUND);`

---

## 🌿 Tips de Thymeleaf para evitar errores de sintaxis

- Declara el namespace: `<html xmlns:th="http://www.thymeleaf.org">`.
- Fragmentos: `~{carpeta/archivo :: nombreFragmento(param1, param2)}` — **sin** `.html` y **sin** `templates/`.
- El nombre del fragmento debe coincidir con el `th:fragment` declarado (`layout(title, content)`).
- URLs con parámetros: `th:href="@{/juegos/editar/{id}(id=${juego.id})}"`, no concatenes strings a mano.
- Concatenar texto: `th:text="|Hola, ${usuario.nombre}|"`.
- Evita `NullPointerException` en la vista con el operador seguro: `${juego?.usuario?.nombre}`.
- `th:field="*{campo}"` solo funciona dentro de un `<form th:object="${objeto}">`.
- El nombre en `${juego.titulo}` debe ser **igual** al atributo de la clase (usa el getter).
- Sesión en la vista: usa `${session.usuario_id}`. En Thymeleaf 3.1 `#session` y `#request` ya **no** existen.
- `th:if` y `th:unless` son opuestos; no repitas la misma condición en ambos.
- Comentarios: `<!-- -->` se ven en el código fuente del navegador; `<!--/* */-->` no. **Nunca** pongas datos sensibles en el HTML.
- Pon `spring.thymeleaf.cache=false` en desarrollo para ver cambios sin reiniciar.

**Leyendo errores comunes:**

| Error | Causa probable |
|---|---|
| `TemplateInputException` | Plantilla o fragmento con nombre/ruta incorrecta |
| `Exception evaluating SpringEL expression` | Atributo mal escrito o variable `null` |
| `Property or field 'x' cannot be found` | La clase no tiene ese campo/getter |
| `Neither BindingResult nor plain target object` | Usaste `th:field` sin `th:object` o sin agregar el objeto al `Model` |

> El mensaje de error siempre indica **archivo y línea**. Léelo antes de cambiar código al azar.

---

## 🎯 Reto

Intenta "hackear" tu propia app:
1. Cierra sesión y entra directo a `/mis-juegos`.
2. Con dos usuarios, intenta editar el juego del otro cambiando el ID en la URL.
3. Envía un `POST` con Postman a cada ruta que modifique datos sin estar logueado.

Si alguno funciona, encontraste un hueco de seguridad. **Arréglalo.**
