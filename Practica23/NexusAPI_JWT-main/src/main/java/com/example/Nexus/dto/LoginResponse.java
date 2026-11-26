package com.example.Nexus.dto;

import com.example.Nexus.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Respuesta del login.
 *
 * Además del token devolvemos los datos públicos del usuario
 * (id, username, email). Así el frontend puede mostrar quién
 * inició sesión sin tener que decodificar el token.
 *
 * { "token": "eyJhbGciOi...", "user": { "id": 1, "username": "carlos_dev", "email": "carlos@nexus.com" } }
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private User user;   // el password no se envía gracias a @JsonIgnore en User
}
