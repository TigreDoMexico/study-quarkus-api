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
        return service.GetAllAsync().map(data -> Response.ok(data).build());
    }

    @GET
    @Path("/get/{id}")
    public Uni<Response> getById(@PathParam("id") int id) {
        return service.GetAsync(id).map(data -> {
            if(data == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(data).build();
            });
    }
}
