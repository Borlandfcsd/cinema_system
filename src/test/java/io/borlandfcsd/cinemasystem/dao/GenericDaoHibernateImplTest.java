package io.borlandfcsd.cinemasystem.dao;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.Movie;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.HSQLDialect;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class GenericDaoHibernateImplTest {
    private static final String HBM_DIR_PREFIX = "./src";
    private GenericDaoHibernateImpl movieDao = new GenericDaoHibernateImpl(Movie.class);
    private Serializable movieId;

    public GenericDaoHibernateImplTest(){
        Configuration configuration  = new Configuration();
        configuration.setProperty(Environment.DRIVER,"org.hsqldb.jdbcDriver");
        configuration.setProperty(Environment.URL,"jdbc:hsqldb:mem:Test");
        configuration.setProperty(Environment.USER,"sa");
        configuration.setProperty(Environment.DIALECT,HSQLDialect .class.getName());
        configuration.setProperty(Environment.CACHE_PROVIDER_CONFIG,"org.hibernate.cache.NoCacheProvider");
        configuration.setProperty(Environment.SHOW_SQL,"true");
        configuration.setProperty(Environment.HBM2DDL_AUTO,"create-drop");
        configuration.setProperty(Environment.HBM2DDL_IMPORT_FILES,"drop-db.sql");
        configuration.setProperty(Environment.HBM2DDL_IMPORT_FILES,"create-db.sql");
        configuration.setProperty(Environment.HBM2DDL_IMPORT_FILES,"insert-data.sql");
        configuration.addResource("Movie.hbm.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        movieDao.setSessionFactory(sessionFactory);
    }

    @Test
    public void addEntityTest() {
        Movie movie = new Movie();
        movie.setTitle("Robocop");
        movie.setDuration(124);
        movieId = movieDao.addEntity(movie);
        assertNotNull(movieId);
    }
/*
    @Test
    public void getEntityTest() {
       Movie movie = (Movie) movieDao.getEntity(1);
        System.out.println(movie.getTitle() + " " + movie.getId());
       assertNotNull(movie);
    }
*/

/*
    @Test
    public void updateEntity() {
    }

    @Test
    public void removeEntity() {
    }

    @Test
    public void getListEntities() {
    }*/
}