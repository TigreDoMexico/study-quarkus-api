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
        return superheroClientService.GetAllAsync().map(returnData -> returnData.map(superheroMapper::toSuperheroEntityList).orElse(null));
    }

    public Uni<Superhero> GetAsync(int id) {
        return persistenceDataService.get(String.valueOf(id))
                .onItem().ifNull().switchTo(() -> {
                    log.infof("No super found in PersistenceData with id %s. Getting data from Rest Client", id);
                    return superheroClientService.GetByIdAsync(id)
                            .map(returnData -> returnData.map(superheroMapper::toSuperheroEntity).orElse(null))
                            .flatMap(superhero -> {
                                if (superhero != null)
                                    return persistenceDataService.set(superhero).map(item -> superhero);
                                return Uni.createFrom().item((Superhero) null);
                            });
                });
    }
}
