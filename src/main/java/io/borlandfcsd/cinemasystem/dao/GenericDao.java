package io.borlandfcsd.cinemasystem.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    PK addEntity(T newInstance);

    T getEntity(PK id);

    public List<T> getEntitiesByColumnName(String column, String data);

    public List<T> getEntitiesByColumnName(String column, int data);

    List<T> getListEntities();

    void updateEntity(T transientObject);

    void removeEntity(PK id);
}