package com.example.GameVault.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.GameVault.model.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// Lógica de negocio de juegos: guardar, buscar y subir portadas.
@Service
public class JuegoService {

    // Spring inyecta el repositorio; no hagas new JuegoRepository().
    @Autowired
    private JuegoRepository juegoRepository;

    // required = false: la app arranca aunque Cloudinary no esté configurado.
    @Autowired(required = false)
    private Cloudinary cloudinary;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }

    public void guardarJuego(Juego juego, MultipartFile portada) {
        // Si es edición y no se subió imagen nueva, se conserva la anterior.
        String urlImagen = juego.getPortadaUrl() != null ? juego.getPortadaUrl() : "default.png";

        if (portada != null && !portada.isEmpty()) {
            // Para guardar en disco cambia a: urlImagen = guardarImagenLocal(portada);
            urlImagen = guardarImagenCloudinary(portada);
        }

        juego.setPortadaUrl(urlImagen);

        juegoRepository.save(juego);
    }

    // Busca en título y categoría; si no hay texto, regresa todo.
    public List<Juego> buscarPorTitulo(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return listarTodos();
        }
        return juegoRepository.buscarPorTituloOCategoria(palabra);
    }

    public Juego obtenerJuegoPorId(Long id) {
        return juegoRepository.findById(id).orElse(null);
    }

    public void eliminarJuego(Long id) {
        juegoRepository.deleteById(id);
    }

    public List<Juego> obtenerJuegosPorUsuario(Long usuarioId) {
        return juegoRepository.findByUsuarioId(usuarioId);
    }

    // Guarda la imagen en static/uploads con un nombre único (UUID) para evitar choques.
    private String guardarImagenLocal(MultipartFile portada) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nombreArchivo = UUID.randomUUID().toString() + "_" + portada.getOriginalFilename();
            Path filePath = uploadPath.resolve(nombreArchivo);
            Files.copy(portada.getInputStream(), filePath);

            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            return "default.png";
        }
    }

    // Sube la imagen a Cloudinary y regresa su URL https.
    private String guardarImagenCloudinary(MultipartFile portada) {
        try {
            Map uploadResult = cloudinary.uploader().upload(portada.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            System.err.println("Error al subir a Cloudinary. Revisa tus credenciales.");
            e.printStackTrace();
            return "default.png";
        }
    }
}
