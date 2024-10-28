package edu.escuelaing.StreamIt.controllers;

import edu.escuelaing.StreamIt.entities.Post;
import edu.escuelaing.StreamIt.repositories.PostRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/stream")
@Produces(MediaType.APPLICATION_JSON)
public class StreamController {

    @Inject
    PostRepository postRepository;

    @GET
    public List<Post> getStream() {
        return postRepository.find("ORDER BY timestamp DESC").list();
    }
}
