package org.example.services.restclient;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ProcessingException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class SuperheroClientService {
    @RestClient
    ISuperheroClientService superheroClientService;
    @Inject
    Logger log;

    public Optional<SuperheroData> GetById(int id) {
        Set<SuperheroData> superFound;

        try {
            superFound = superheroClientService.getById(id);
        } catch(ProcessingException ex) {
            log.warn("Error while get data from Superhero Rest Client", ex);
            return Optional.empty();
        }

        if (superFound.isEmpty()) {
            log.infof("No data return from Superhero Rest Client with Id %d", id);
            return Optional.empty();
        }

        Iterator<SuperheroData> iterator = superFound.iterator();
        if (iterator.hasNext()) {
            return Optional.ofNullable(iterator.next());
        }

        return Optional.empty();
    }

    public Optional<List<SuperheroData>> GetAll() {
        Set<SuperheroData> superFound;
        try {
            superFound = superheroClientService.getAll();
        } catch(ProcessingException ex) {
            log.warn("Error while get data from Superhero Rest Client", ex);
            return Optional.empty();
        }

        if (superFound.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(List.copyOf(superFound));
    }
}
