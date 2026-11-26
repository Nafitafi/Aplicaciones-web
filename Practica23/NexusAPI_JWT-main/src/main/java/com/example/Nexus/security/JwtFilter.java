package com.example.Nexus.security;

import com.example.Nexus.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ============================================================
 *  JwtFilter — Filtro HTTP que protege los endpoints
 * ============================================================
 *
 * ¿Qué es un filtro (Filter)?
 * Un filtro intercepta CADA petición HTTP ANTES de que llegue
 * al Controller. Es como una "aduana": revisa que el request
 * tenga los papeles en regla antes de dejarlo pasar.
 *
 * Este filtro extiende OncePerRequestFilter de Spring, que
 * garantiza que el filtro se ejecuta exactamente UNA VEZ
 * por request (evita duplicados en ciertos escenarios de Spring).
 *
 * Flujo de cada petición:
 *
 *   [Cliente] → [JwtFilter] → ¿Ruta pública? → SÍ → [Controller]
 *                                              → NO → ¿Tiene token?
 *                                                      → SÍ → ¿Token válido?
 *                                                               → SÍ → [Controller]
 *                                                               → NO → 401
 *                                                      → NO → 401
 *
 * IMPORTANTE: Este filtro NO modifica ningún Controller existente.
 * Es completamente transparente para el resto del código.
 *
 * @Component → Spring registra esta clase como un bean.
 *              NexusApplication la registra como filtro activo.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Inyectamos el servicio JWT para poder validar tokens.
     * Spring lo inyecta automáticamente.
     */
    @Autowired
    private JwtService jwtService;

    // ================================================================
    //  Definir las rutas que NO requieren token (rutas públicas)
    // ================================================================

    /**
     * Define si una ruta es pública (no requiere autenticación).
     *
     * Regla simple: SOLO login y registro son públicos.
     * Todo lo demás (posts y comentarios) pide token.
     *
     *   POST /api/auth/login     → aquí es donde SE OBTIENE el token
     *   POST /api/auth/register  → crear cuenta
     *
     * @param request la petición HTTP actual
     * @return true si la ruta es pública (no necesita token)
     */
    private boolean isPublicRoute(HttpServletRequest request) {
        String method = request.getMethod();    // GET, POST, PUT, DELETE...
        String path   = request.getRequestURI(); // /api/auth/login, /api/posts, etc.

        return method.equals("POST")
                && (path.equals("/api/auth/login") || path.equals("/api/auth/register"));
    }

    // ================================================================
    //  Lógica principal del filtro
    // ================================================================

    /**
     * Este método se ejecuta en CADA petición HTTP.
     *
     * Parámetros que recibe Spring automáticamente:
     * @param request  el objeto que representa la petición HTTP del cliente
     * @param response el objeto que representa la respuesta que devolveremos
     * @param chain    la cadena de filtros: llamar a chain.doFilter() significa
     *                 "dejar pasar este request al siguiente filtro o al Controller"
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // NOTA PARA LOS ALUMNOS: Esto es SOLO para entorno de pruebas/desarrollo.
        // Se abren los permisos de CORS para que cualquier frontend pueda consumir esta API sin bloqueos.
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, Origin, X-Requested-With");

        // Si es una petición preflight (OPTIONS), respondemos OK (200) de inmediato sin validar token
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // ── Paso 1: ¿Es una ruta pública? ──────────────────────────────
        if (isPublicRoute(request)) {
            // Dejamos pasar sin validar nada
            chain.doFilter(request, response);
            return; // Terminamos aquí, no seguimos con la validación
        }

        // ── Paso 2: Leer el header "Authorization" ─────────────────────
        //
        // El estándar HTTP define que los tokens JWT se envían así:
        //   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
        //
        // "Bearer" es el esquema de autenticación (token portador).
        // Nosotros solo necesitamos la parte después de "Bearer ".
        //
        String authHeader = request.getHeader("Authorization");

        // ── Paso 3: Verificar que el header existe y tiene el formato correcto ──
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No hay token → respondemos 401 Unauthorized
            sendUnauthorized(response, "Se requiere token de autenticación");
            return;
        }

        // ── Paso 4: Extraer el token (quitarle el prefijo "Bearer ") ───
        // "Bearer " tiene 7 caracteres, entonces substring(7) nos da solo el token
        String token = authHeader.substring(7);

        // ── Paso 5: Validar el token ────────────────────────────────────
        if (!jwtService.isTokenValid(token)) {
            // Token inválido (vencido, firma incorrecta, malformado) → 401
            sendUnauthorized(response, "Token inválido o expirado");
            return;
        }

        // ── Paso 6: Token válido → dejar pasar al Controller ───────────
        //
        // Guardamos el username (subject del token) como atributo del request.
        // Esto permite que cualquier Controller recupere el username del token
        // con: request.getAttribute("username")
        // → Se usa para crear posts, comentar y en GET /api/posts/mine.
        //   Así el cliente nunca manda su id: la identidad sale del token firmado.
        //
        String username = jwtService.extractUsername(token);
        request.setAttribute("username", username);

        // Continuamos la cadena: el request pasa al Controller
        chain.doFilter(request, response);
    }

    // ================================================================
    //  Método auxiliar: enviar respuesta 401
    // ================================================================

    /**
     * Escribe una respuesta 401 Unauthorized en formato JSON.
     *
     * No podemos usar ResponseEntity aquí porque estamos en un Filtro,
     * no en un Controller. Usamos el HttpServletResponse directamente
     * para escribir la respuesta.
     *
     * @param response el objeto de respuesta HTTP
     * @param message  el mensaje de error a incluir en el JSON
     */
    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // código 401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Construimos el JSON manualmente. Para un objeto tan sencillo
        // ({ "error": "..." }) no necesitamos una librería extra.
        String json = String.format("{\"error\": \"%s\"}", message);
        response.getWriter().write(json);
    }
}
