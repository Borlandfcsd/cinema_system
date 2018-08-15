package io.borlandfcsd.cinemasystem.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;


public class GenericDaoHibernateImpl<T, PK extends Serializable>
        implements GenericDao<T, PK> {

    private static final Logger log = Logger.getLogger(GenericDaoHibernateImpl.class);
    private Class<T> type;
    private SessionFactory sessionFactory;

    public GenericDaoHibernateImpl(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public PK addEntity(T o) {
        Session session = getSession();
        session.beginTransaction();
        Serializable pk = session.save(o);
        session.getTransaction().commit();
        return (PK) pk;
    }

    public T getEntity(PK id) {
        return (T) getSession().get(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getEntitiesByColumnName(String column, String data) {
        Query query = getSession().createQuery("from " + type.getName() + " where " + column + " = :pData");
        query.setParameter("pData", data);
        log.info("Entity \"" + data + "\" success found");
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> getEntitiesByColumnName(String column, int data) {
        Query query = getSession().createQuery("from " + type.getName() + " where " + column + " = :pData");
        query.setParameter("pData", data);
        log.info("Entity \"" + data + "\" success found");
        return query.list();
    }

    public void updateEntity(T o) {
        Session session = getSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        log.info("Update of entity " + o.toString() + " finished");
    }

    public void removeEntity(PK id) {
        Session session = getSession();
        session.beginTransaction();
        T entity = session.load(type, id);
        if (entity != null) {
            session.delete(entity);
        }
        session.getTransaction().commit();
        log.info("Entity with id=" + id + " removed");
    }

    @SuppressWarnings(value = "unchecked")
    public List<T> getListEntities() {
        Query query = getSession().createQuery("from " + type.getName());
        log.info("List of entities found ");
        return query.list();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
