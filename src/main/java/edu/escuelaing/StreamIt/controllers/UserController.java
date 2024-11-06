package edu.escuelaing.StreamIt.controllers;

import edu.escuelaing.StreamIt.entities.UserEntity;
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
    public List<UserEntity> getAllUsers() {
        return userRepository.listAll();
    }

    @POST
    @Transactional
    public UserEntity createUser(UserEntity user) {
        userRepository.persist(user);
        return user;
    }

    @GET
    @Path("/{id}")
    public UserEntity getUserById(@PathParam("id") Long id) {
        return userRepository.findById(id);
    }

    @POST
    @Path("/auth/login")
    public UserEntity login(UserEntity user) {
        UserEntity userEntity = userRepository.find("email", user.getEmail()).firstResult();
        if (userEntity == null || !userEntity.getPassword().equals(user.getPassword())) {
            throw new WebApplicationException("Invalid email or password", 401);
        }
        return userEntity;
    }
}
