package com.example.Nexus.controllers;

import com.example.Nexus.dto.PostRequest;
import com.example.Nexus.models.Post;
import com.example.Nexus.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================
 *  PostController — Publicaciones (todas requieren token)
 * ============================================================
 *
 *   GET  /api/posts       → todos los posts (el feed)
 *   GET  /api/posts/mine  → solo mis posts
 *   POST /api/posts       → crear un post a mi nombre
 *
 * ¿Cómo sabemos quién es "yo"?
 * El JwtFilter valida el token y guarda el username (el "sub"
 * del token) en el request:  request.setAttribute("username", ...)
 * Aquí lo leemos con request.getAttribute("username").
 * El cliente NUNCA envía su id: no puede hacerse pasar por otro.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /** GET /api/posts → todos los posts, del más reciente al más antiguo. */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /** GET /api/posts/mine → los posts del usuario dueño del token. */
    @GetMapping("/mine")
    public ResponseEntity<List<Post>> getMyPosts(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return ResponseEntity.ok(postService.getMyPosts(username));
    }

    /**
     * POST /api/posts → crea un post.
     * Body: { "title": "...", "content": "..." }
     * El autor se toma del token, no del body.
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostRequest body,
                                           HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Post post = postService.createPost(body, username);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
}
