package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.GenericDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import java.util.Collection;

@Repository
public abstract class GenericDAOImpl<GenericEntity> implements GenericDAO<GenericEntity> {
    protected Class<GenericEntity> entityClass;

    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }

    public GenericDAOImpl(Class<GenericEntity> entityClass) {
        this.entityClass = entityClass;
    }

    Session openSession() {
        return this.sessionFactory.openSession();
    }

    @Override
    public void save(GenericEntity entity) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveCollection(Collection<GenericEntity> entities) {
        try (Session session = openSession()) {
            session.beginTransaction();
            for (GenericEntity entity : entities) {
                this.save(entity);
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
    public Collection<GenericEntity> getAll() {
        try (Session session = openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<GenericEntity> criteriaQuery = builder.createQuery(entityClass);
            criteriaQuery.from(entityClass);
            return session.createQuery(criteriaQuery).getResultList();
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
            session.getTransaction().commit();
        }
    }
}
