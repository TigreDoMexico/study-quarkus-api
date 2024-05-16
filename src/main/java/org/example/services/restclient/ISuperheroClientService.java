package org.example.services.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;
import java.util.concurrent.CompletionStage;

@Path("/superheroes")
@RegisterRestClient(configKey="superheroes-api")
public interface ISuperheroClientService {
    @GET
    CompletionStage<Set<SuperheroData>> getByIdAsync(@QueryParam("id") int id);

    @GET
    CompletionStage<Set<SuperheroData>> getAllAsync();
}
