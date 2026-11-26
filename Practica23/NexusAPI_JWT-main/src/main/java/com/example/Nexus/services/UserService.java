package com.example.Nexus.services;

import com.example.Nexus.dto.UserRequest;
import com.example.Nexus.exceptions.ResourceNotFoundException;
import com.example.Nexus.models.User;
import com.example.Nexus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Lógica de negocio de usuarios.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /** Registra un usuario. Si el username ya existe responde 400. */
    public User createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return userRepository.save(user);
    }

    /** Busca un usuario por username (el username viene del token). */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
    }
}
