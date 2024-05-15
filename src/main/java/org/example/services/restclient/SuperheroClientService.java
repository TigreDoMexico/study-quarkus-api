package org.example.services.restclient;

import jakarta.inject.Singleton;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Singleton
public class SuperheroClientService {
    @RestClient
    ISuperheroClientService superheroClientService;

    public Optional<SuperheroData> GetById(int id) {
        var superFound = superheroClientService.getById(id);
        if (superFound.isEmpty()) {
            return Optional.empty();
        }

        Iterator<SuperheroData> iterator = superFound.iterator();
        if (iterator.hasNext()) {
            return Optional.ofNullable(iterator.next());
        }

        return Optional.empty();
    }

    public Optional<List<SuperheroData>> GetAll() {
        var superFound = superheroClientService.getAll();
        if (superFound.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(List.copyOf(superFound));
    }
}
