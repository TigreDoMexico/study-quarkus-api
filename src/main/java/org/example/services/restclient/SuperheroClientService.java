package org.example.services.restclient;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ProcessingException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@Singleton
public class SuperheroClientService {
    @RestClient
    ISuperheroClientService superheroClientService;
    @Inject
    Logger log;

    public Uni<Optional<SuperheroData>> GetByIdAsync(int id) {
        try {
            return Uni.createFrom().completionStage(superheroClientService.getByIdAsync(id))
                    .flatMap(data -> {
                        if (data.isEmpty()) {
                            log.infof("No data return from Superhero Rest Client with Id %d", id);
                            return Uni.createFrom().item(Optional.empty());
                        } else {
                            return Uni.createFrom().item(Optional.ofNullable(data.iterator().next()));
                        }
                    });
        } catch(ProcessingException ex) {
            log.warn("Error while get data from Superhero Rest Client", ex);
            return Uni.createFrom().item(Optional.empty());
        }
    }

    public Uni<Optional<List<SuperheroData>>> GetAllAsync() {
        try {
            return Uni.createFrom().completionStage(superheroClientService.getAllAsync())
                    .flatMap(data -> {
                        if (data.isEmpty()) {
                            log.info("No data return from Superhero Rest Client");
                            return Uni.createFrom().item(Optional.empty());
                        } else {
                            return Uni.createFrom().item(Optional.ofNullable(List.copyOf(data)));
                        }
                    });
        } catch(ProcessingException ex) {
            log.warn("Error while get data from Superhero Rest Client", ex);
            return Uni.createFrom().item(Optional.empty());
        }
    }
}
