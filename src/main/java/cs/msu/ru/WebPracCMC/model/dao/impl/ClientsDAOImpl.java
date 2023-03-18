package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.ClientsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ClientsDAOImpl extends GenericDAOImpl<Clients>
        implements ClientsDAO {
    public ClientsDAOImpl() {
        super(Clients.class);
    }

    @Override
    public Collection<Clients> getClientsByFilter(Filter filter) {
        try (Session session = openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Clients> criteriaQuery = builder.createQuery(Clients.class);
            Root<Clients> root = criteriaQuery.from(Clients.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(builder.equal(root.get("clientId"), filter.getId()));
            }
            if (filter.getFullName() != null && !filter.getFullName().isBlank()) {
                String pattern = getPattern(filter.getFullName());
                predicates.add(builder.like(root.get("fullName"), pattern));
            }
            if (filter.getPreferredCity() != null && !filter.getPreferredCity().isBlank()) {
                String pattern = getPattern(filter.getPreferredCity());
                predicates.add(builder.like(root.get("preferredCity"), pattern));
            }

            if (!predicates.isEmpty()) {
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            }

            List<Clients> result = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }

    private String getPattern(String string) {
        return "%" + string + "%";
    }
}
