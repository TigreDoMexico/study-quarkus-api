package org.example.resource;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.services.SuperheroService;

@Path("/superhero")
public class SuperheroResource {
    @Inject
    public SuperheroService service;

    @GET
    @Path("/list")
    public Uni<Response> list() {
        return service.GetAllAsync().onItem().transform(data -> Response.ok(data).build());
    }

    @GET
    @Path("/get/{id}")
    public Uni<Response> getById(@PathParam("id") int id) {
        return service.GetAsync(id)
                //.onItem().ifNull().fail().replaceWith(() -> Response.status(Response.Status.NOT_FOUND).build())
                .onItem().transform(data -> Response.ok(data).build());
    }
}
