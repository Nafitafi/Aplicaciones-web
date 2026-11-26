package com.example.Nexus.services;

import com.example.Nexus.dto.CommentRequest;
import com.example.Nexus.models.Comment;
import com.example.Nexus.models.Post;
import com.example.Nexus.models.User;
import com.example.Nexus.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica de negocio de comentarios.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    /**
     * Crea un comentario a nombre del usuario autenticado.
     *
     * @param request  postId y texto
     * @param username el username que viene del token JWT
     */
    public Comment createComment(CommentRequest request, String username) {
        Post post = postService.getPostById(request.getPostId()); // 404 si no existe
        User user = userService.getUserByUsername(username);

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    /** Comentarios de un post. 404 si el post no existe. */
    public List<Comment> getCommentsByPost(Long postId) {
        postService.getPostById(postId);
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }
}
