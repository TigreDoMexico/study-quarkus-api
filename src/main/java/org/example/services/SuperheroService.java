package org.example.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.Superhero;
import org.example.services.restclient.SuperheroClientService;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class SuperheroService {
    @Inject
    SuperheroClientService superheroClientService;
    @Inject
    SuperheroMapper superheroMapper;
    @Inject
    PersistenceDataService<Superhero> persistenceDataService;
    @Inject
    Logger log;

    public List<Superhero> GetAll() {
        var data = superheroClientService.GetAll();
        return data.map(superheroMapper::toSuperheroEntityList).orElse(null);
    }

    public Superhero Get(int id) {
        Superhero superheroFound;

        superheroFound = persistenceDataService.get(String.valueOf(id));
        if(superheroFound == null) {
            log.infof("No super found in PersistenceData with id %s. Getting data from Rest Client", id);

            superheroFound = superheroClientService.GetById(id).map(superheroMapper::toSuperheroEntity).orElse(null);
            if(superheroFound != null) {
                persistenceDataService.set(superheroFound);
            }
        }

        return superheroFound;
    }
}
