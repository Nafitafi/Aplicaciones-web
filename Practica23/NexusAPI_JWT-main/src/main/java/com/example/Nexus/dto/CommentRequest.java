package com.example.Nexus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Datos que el cliente envía para comentar un post.
 *
 * Ya NO lleva userId: el autor se obtiene del token JWT.
 *
 * { "postId": 1, "text": "Buen post" }
 */
@Data
public class CommentRequest {

    @NotBlank(message = "El texto es obligatorio")
    private String text;

    @NotNull(message = "El postId es obligatorio")
    private Long postId;
}
