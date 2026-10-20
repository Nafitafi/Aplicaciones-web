package com.example.GameVault.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Entity mapea esta clase a la tabla "juegos" en MySQL.
@Data // Lombok genera getters/setters; Thymeleaf los usa en ${juego.titulo}.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "juegos")
public class Juego {

    // Llave primaria con AUTO_INCREMENT.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable = false: la BD rechaza juegos sin título.
    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000) // Permite descripciones largas.
    private String descripcion;

    // Guarda la URL de Cloudinary o el nombre del archivo local.
    @Column(name = "portada_url")
    private String portadaUrl;

    @Column
    private Double precio;

    @Column
    private String categoria;

    // Dueño del juego (muchos juegos → un usuario); úsalo para validar permisos.
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}

