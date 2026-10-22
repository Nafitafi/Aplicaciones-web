package com.example.GameVault.service;

import com.example.GameVault.model.Usuario;
import com.example.GameVault.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Registro, login y lectura del usuario en sesión.
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Spring inyecta la sesión de la petición actual.
    @Autowired
    private HttpSession session;

    public Usuario registrarUsuario(Usuario usuario) {
        // Nunca guardes la contraseña en texto plano: se cifra con BCrypt.
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // matches() compara la contraseña escrita con el hash guardado.
            if (passwordEncoder.matches(password, usuario.getContrasenia())) {
                return usuario;
            }
        }
        return null; // No digas si falló el correo o la contraseña: le das pistas a un atacante.
    }

    // Fuente confiable de "quién soy": la sesión del servidor. Regresa null si no hay sesión.
    public Usuario obtenerUsuarioLogueado() {
        Long usuarioId = (Long) session.getAttribute("usuario_id");

        if (usuarioId != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isPresent()) {
                return usuarioOpt.get();
            }
        }
        return null;
    }
}
