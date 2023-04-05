package cs.msu.ru.WebPracCMC.model.dao;

import java.util.Collection;

public interface GenericDAO<GenericEntity> {
    void save(GenericEntity entity);
    void saveCollection(Collection<GenericEntity> entities);
    void update(GenericEntity entity);
    void delete(GenericEntity entity);
    GenericEntity getById(Integer id);
    Collection<GenericEntity> getAll();
    void deleteById(Integer id);
}
