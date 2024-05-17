package org.example.persistence;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.example.model.Superhero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.jboss.logging.Logger;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class SuperheroPersistenceDataServiceTests {
    @Mock
    ReactiveRedisDataSource dataSource;

    @Mock
    ReactiveValueCommands<String, Superhero> countCommand;

    @Mock
    Logger log;

    @InjectMocks
    SuperheroPersistenceDataService superheroPersistenceDataService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(dataSource.value(Superhero.class)).thenReturn(countCommand);
    }

    @Test
    public void testGet() {
        String key = "1";
        Superhero superhero = new Superhero();
        superhero.id = 10L;
        superhero.name = "Superman";

        when(countCommand.get(any(String.class))).thenReturn(Uni.createFrom().item(superhero));

        Uni<Superhero> result = superheroPersistenceDataService.get(key);

        assertNotNull(result);
        result.subscribe().with(item -> assertEquals("Superman", item.name));
    }

    @Test
    public void testSet() {
        Superhero superhero = new Superhero();
        superhero.id = 10L;
        superhero.name = "Superman";

        when(countCommand.set(any(String.class), any(Superhero.class))).thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = superheroPersistenceDataService.set(superhero);

        assertNotNull(result);
        result.subscribe().with(item -> assertTrue(true));
    }
}
