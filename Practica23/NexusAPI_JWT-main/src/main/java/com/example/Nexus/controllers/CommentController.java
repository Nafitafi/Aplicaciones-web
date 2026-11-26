package com.example.Nexus.controllers;

import com.example.Nexus.dto.CommentRequest;
import com.example.Nexus.models.Comment;
import com.example.Nexus.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================
 *  CommentController — Comentarios (requieren token)
 * ============================================================
 *
 *   GET  /api/comments/post/{postId} → comentarios de un post
 *   POST /api/comments               → comentar a mi nombre
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /** GET /api/comments/post/{postId} → comentarios del post, del más antiguo al más nuevo. */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    /**
     * POST /api/comments → crea un comentario.
     * Body: { "postId": 1, "text": "..." }
     * El autor se toma del token, no del body.
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentRequest body,
                                                 HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Comment comment = commentService.createComment(body, username);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
}
