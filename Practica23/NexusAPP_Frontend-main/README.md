# Práctica 23 — Nexus App (código base)

Frontend de una red social mínima que consume una API REST protegida con **JWT**: iniciar sesión, ver el feed de publicaciones y publicar.

El HTML, el CSS y el HTML de las tarjetas (`js/templates.js`) ya están hechos. Tú completas los `TODO` de JavaScript.

## Antes de empezar

1. Levanta la API: [NexusAPI_JWT](https://github.com/MartinBernal06/NexusAPI_JWT). Debe quedar en `http://localhost:8080/api` con los datos de prueba de su README (usuario `carlos_dev`, contraseña `1234`).
2. Abre esta carpeta en VS Code y corre `login.html` con **Live Server**. Con doble clic (`file://`) los `import` no funcionan.

## Qué vas a programar (en este orden)

| Archivo | Qué completar |
|---|---|
| `js/services/AuthService.js` | `login()`: `POST /auth/login` y guardar `token` y `user` en localStorage |
| `js/pages/login.js` | El `submit` del formulario |
| `js/services/PostService.js` | `getHeaders()`, `getAll()` y `create()` |
| `js/templates.js` | Cambiar los textos en MAYÚSCULAS por `${...}` |
| `js/pages/app.js` | Mostrar el usuario, cargar el feed, abrir/cerrar el modal y publicar |

Cada función tiene sus pasos numerados en los comentarios `TODO`.

> No mandes `userId` al publicar: la API sabe quién eres por el token.

## Entregable

Repositorio de Git público con el proyecto, un `.gitignore` y una carpeta `evidencias/` con imágenes de: el login con error, el feed con tu nombre de usuario, el modal, el post publicado y la pestaña **Network** de DevTools mostrando que el POST no envía `userId`. En iVirtual entrega el enlace.
