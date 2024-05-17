package org.example.persistence;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.Superhero;
import org.example.services.PersistenceDataService;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SuperheroPersistenceDataService extends PersistenceDataService<Superhero> {
    private final ReactiveValueCommands<String, Superhero> countCommand;

    @Inject
    Logger log;

    @Inject
    public SuperheroPersistenceDataService(ReactiveRedisDataSource dataSource) {
        this.countCommand = dataSource.value(getType());
    }

    @Override
    public Uni<Superhero> get(String key) {
        var realKey = generateKey(key);
        log.infof("Getting Redis data from key %s", realKey);

        return countCommand.get(realKey);
    }

    @Override
    public Uni<Void> set(Superhero data) {
        var key = generateKey(data.id.toString());
        log.infof("Setting Redis data with key %s", key);

        return countCommand.set(key,data);
    }

    private String generateKey(String id) {
        return String.format("superhero:%s", id);
    }
}
