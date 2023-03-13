package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.HibernateConfiguration;
import cs.msu.ru.WebPracCMC.model.dao.GenericDAO;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import java.util.Collection;

@Repository
public abstract class GenericDAOImpl<GenericEntity> implements GenericDAO<GenericEntity> {
    protected Class<GenericEntity> entityClass;

    public GenericDAOImpl(Class<GenericEntity> entityClass) {
        this.entityClass = entityClass;
    }

    Session openSession() {
        return HibernateConfiguration.getSessionFactory().openSession();
    }

    @Override
    public void save(GenericEntity entity) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveCollection(Collection<GenericEntity> entities) {
        try (Session session = openSession()) {
            session.beginTransaction();
            for (GenericEntity entity : entities) {
                session.persist(entity);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(GenericEntity entity) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(GenericEntity entity) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public GenericEntity getById(Integer id) {
        try (Session session = openSession()) {
            return session.get(entityClass, id);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = openSession()) {
            session.beginTransaction();
            GenericEntity entity = this.getById(id);
            if (entity != null) {
                session.remove(entity);
            }
        }
    }
}
