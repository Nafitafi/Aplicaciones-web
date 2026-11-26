package com.example.Nexus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Datos que el cliente envía para crear un post.
 *
 * Ya NO lleva userId: el autor se obtiene del token JWT
 * (así nadie puede publicar a nombre de otro).
 *
 * { "title": "Mi post", "content": "Contenido del post" }
 */
@Data
public class PostRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    private String content;
}
