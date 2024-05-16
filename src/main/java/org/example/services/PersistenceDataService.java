package org.example.services;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class PersistenceDataService<T extends PanacheEntity> {
    public abstract T get(String key);
    public abstract void set(T data);

    @SuppressWarnings("unchecked")
    protected Class<T> getType() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        return (Class<T>) pt.getActualTypeArguments()[0];
    }
}
