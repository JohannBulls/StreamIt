package edu.escuelaing.StreamIt.controllers;

import edu.escuelaing.StreamIt.entities.UserEntity;
import edu.escuelaing.StreamIt.repositories.UserRepository;
import edu.escuelaing.StreamIt.services.CognitoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    CognitoService cognitoService;

    @Inject
    UserRepository userRepository;

    @GET
    public List<UserEntity> getAllUsers() {
        return userRepository.listAll();
    }

    @POST
    @Transactional
    public Response createUser(UserEntity user) {
        userRepository.persist(user);
//        cognitoService.createUser(user.getName(), user.getEmail(), user.getPassword());
//        String JwtToken = cognitoService.authenticateUser(user.getEmail(), user.getPassword());
        return Response.ok(user).build();
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

    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
