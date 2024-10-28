package edu.escuelaing.StreamIt.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class RootController {

    @GET
    public Response redirectToIndex() {
        return Response.status(Response.Status.FOUND)
                .location(URI.create("/index.html"))
                .build();
    }
}
