package org.example;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.example.model.Superhero;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/superhero")
public class SuperheroResource {

    private final Set<Superhero> superheroes = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public SuperheroResource() {
        superheroes.add(new Superhero("Batman", "Bruce Wayne"));
        superheroes.add(new Superhero("Superman", "Clark Kent"));
    }

    @GET
    public Response list() {
        return Response.ok(Superhero.listAll()).build();
    }

    @POST
    @Transactional
    public Response add(Superhero superhero) {
        superhero.persist();
        return Response.ok(superhero).build();
    }

    @DELETE
    public Response delete(Superhero fruit) {
        superheroes.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return Response.ok(superheroes).build();
    }
}
