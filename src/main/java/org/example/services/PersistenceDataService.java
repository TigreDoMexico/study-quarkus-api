package org.example.services;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class PersistenceDataService<T extends PanacheEntity> {
    public abstract Uni<T> get(String key);
    public abstract Uni<Void> set(T data);

    @SuppressWarnings("unchecked")
    protected Class<T> getType() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        return (Class<T>) pt.getActualTypeArguments()[0];
    }
}
