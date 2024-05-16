package org.example.persistence;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.model.Superhero;
import org.example.services.PersistenceDataService;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SuperheroPersistenceDataService extends PersistenceDataService<Superhero> {
    private final ValueCommands<String, Superhero> countCommand;
    @Inject
    Logger log;

    public SuperheroPersistenceDataService(RedisDataSource dataSource) {
        this.countCommand = dataSource.value(getType());
    }

    @Override
    public Superhero get(String key) {
        var realKey = generateKey(key);
        log.infof("Getting Redis data from key %s", realKey);

        return countCommand.get(realKey);
    }

    @Override
    public void set(Superhero data) {
        var key = generateKey(data.id.toString());
        log.infof("Setting Redis data with key %s", key);

        countCommand.set(key,data);
    }

    private String generateKey(String id) {
        return String.format("superhero:%s", id);
    }
}
