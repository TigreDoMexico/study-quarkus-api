package org.example.services.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;

@Path("/superheroes")
@RegisterRestClient(configKey="superheroes-api")
public interface ISuperheroClientService {
    @GET
    Set<SuperheroData> getById(@QueryParam("id") int id);

    @GET
    Set<SuperheroData> getAll();
}
