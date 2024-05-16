package org.example.services;

import io.smallrye.mutiny.Uni;
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

    public Uni<List<Superhero>> GetAllAsync() {
        return superheroClientService.GetAllAsync()
                .onItem()
                .transform(returnData -> returnData.map(superheroMapper::toSuperheroEntityList).orElse(null));
    }

    public Uni<Superhero> GetAsync(int id) {
        return persistenceDataService.get(String.valueOf(id))
                .onItem().ifNull().switchTo(() -> {
                    log.infof("No super found in PersistenceData with id %s. Getting data from Rest Client", id);
                    return superheroClientService.GetByIdAsync(id).onItem()
                            .transform(returnData -> returnData.map(superheroMapper::toSuperheroEntity)
                                    .orElse(null))
                            .call(superhero -> {
                                if(superhero != null)
                                    persistenceDataService.set(superhero);
                                return Uni.createFrom().item(superhero);
                            });
                });
    }
}
