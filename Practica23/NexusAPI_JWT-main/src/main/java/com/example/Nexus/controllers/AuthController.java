package com.example.Nexus.controllers;

import com.example.Nexus.dto.LoginRequest;
import com.example.Nexus.dto.LoginResponse;
import com.example.Nexus.dto.UserRequest;
import com.example.Nexus.models.User;
import com.example.Nexus.repositories.UserRepository;
import com.example.Nexus.services.JwtService;
import com.example.Nexus.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ============================================================
 *  AuthController — Registro e inicio de sesión
 * ============================================================
 *
 * Son las ÚNICAS rutas públicas de la API (no piden token):
 *   POST /api/auth/register → crear cuenta
 *   POST /api/auth/login    → obtener el token JWT
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * Registra un nuevo usuario.
     * Body: { "username": "ana", "email": "ana@nexus.com", "password": "1234" }
     *
     * @return 201 Created con el usuario creado (sin password)
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    /**
     * Inicia sesión.
     * Body: { "username": "carlos_dev", "password": "1234" }
     *
     * 1. Busca al usuario por username.
     * 2. Compara la contraseña (texto plano, solo con fines didácticos).
     * 3. Genera un token cuyo "subject" (sub) es el username.
     *
     * @return 200 OK con { token, user } o 401 si las credenciales son incorrectas
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        // Mismo mensaje en ambos casos para no revelar si el usuario existe
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponse(token, user));
    }
}
