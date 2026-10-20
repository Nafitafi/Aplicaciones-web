package com.example.GameVault.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// Guardia de rutas: se ejecuta ANTES del controlador y bloquea a quien no tenga sesión.
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // false: no crea una sesión nueva, solo revisa si ya existe.
        HttpSession session = request.getSession(false);

        // Sin sesión o sin usuario_id = no está logueado; lo mandamos al login.
        if (session == null || session.getAttribute("usuario_id") == null) {
            response.sendRedirect("/login");
            return false; // false detiene la petición; el controlador nunca se ejecuta.
        }

        return true; // true deja pasar la petición al controlador.
    }
}
