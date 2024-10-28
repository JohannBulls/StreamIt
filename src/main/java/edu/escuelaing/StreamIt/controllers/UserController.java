package edu.escuelaing.StreamIt.controllers;

import edu.escuelaing.StreamIt.entities.User;
import edu.escuelaing.StreamIt.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    public List<User> getAllUsers() {
        return userRepository.listAll();
    }

    @POST
    @Transactional
    public User createUser(User user) {
        userRepository.persist(user);
        return user;
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") Long id) {
        return userRepository.findById(id);
    }
}
