package edu.escuelaing.StreamIt.controllers;

import edu.escuelaing.StreamIt.entities.Post;
import edu.escuelaing.StreamIt.entities.UserEntity;
import edu.escuelaing.StreamIt.repositories.PostRepository;
import edu.escuelaing.StreamIt.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostRepository postRepository;

    @Inject
    UserRepository userRepository;

    @GET
    public List<Post> getAllPosts() {
        return postRepository.listAll();
    }

    @POST
    @Transactional
    public Post createPost(Post post, @QueryParam("userId") Long userId) {
        UserEntity author = userRepository.findById(userId);
        post.setAuthor(author);
        postRepository.persist(post);
        return post;
    }

    @GET
    @Path("/{id}")
    public Post getPostById(@PathParam("id") Long id) {
        return postRepository.findById(id);
    }
}
