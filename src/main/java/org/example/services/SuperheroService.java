package org.example.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.Superhero;
import org.example.services.restclient.SuperheroClientService;

import java.util.List;

@ApplicationScoped
public class SuperheroService {
    @Inject
    SuperheroClientService superheroClientService;
    @Inject
    SuperheroMapper superheroMapper;

    public List<Superhero> GetAll() {
        var data = superheroClientService.GetAll();
        return data.map(superheroMapper::toSuperheroEntityList).orElse(null);
    }

    public Superhero Get(int id) {
        var superFound = superheroClientService.GetById(id);
        return superFound.map(superheroMapper::toSuperheroEntity).orElse(null);
    }
}
