package edu.escuelaing.StreamIt.repositories;

import edu.escuelaing.StreamIt.entities.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
