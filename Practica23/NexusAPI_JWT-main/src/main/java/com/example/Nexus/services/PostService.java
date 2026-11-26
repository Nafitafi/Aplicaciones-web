package com.example.Nexus.services;

import com.example.Nexus.dto.PostRequest;
import com.example.Nexus.exceptions.ResourceNotFoundException;
import com.example.Nexus.models.Post;
import com.example.Nexus.models.User;
import com.example.Nexus.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica de negocio de posts.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    /**
     * Crea un post a nombre del usuario autenticado.
     *
     * @param request  título y contenido
     * @param username el username que viene del token JWT
     */
    public Post createPost(PostRequest request, String username) {
        User user = userService.getUserByUsername(username);

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        return postRepository.save(post);
    }

    /** Todos los posts, del más reciente al más antiguo. */
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    /** Solo los posts del usuario autenticado. */
    public List<Post> getMyPosts(String username) {
        return postRepository.findByUserUsernameOrderByCreatedAtDesc(username);
    }

    /** Busca un post por id (se usa al comentar). 404 si no existe. */
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con ID: " + id));
    }
}
