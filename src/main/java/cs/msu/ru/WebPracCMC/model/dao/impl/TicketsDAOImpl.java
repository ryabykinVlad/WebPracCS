package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.TicketsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Tickets;
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
public class TicketsDAOImpl extends GenericDAOImpl<Tickets>
        implements TicketsDAO {
    public TicketsDAOImpl() {
        super(Tickets.class);
    }
    @Override
    public Collection<Tickets> getTicketsByFilter(TicketsDAO.Filter filter) {
        try (Session session = openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tickets> criteriaQuery = builder.createQuery(Tickets.class);
            Root<Tickets> root = criteriaQuery.from(Tickets.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("clientId"), filter.getClientId()));
            }
            if (filter.getFlightId() != null) {
                predicates.add(builder.equal(root.get("flightId"), filter.getFlightId()));
            }
            if (filter.getDepartureDate() != null) {
                var start = filter.getDepartureDate().atStartOfDay();
                var end = start.plusDays(1);
                predicates.add(builder.between(root.get("departure_time"), start, end));
            }

            if (!predicates.isEmpty()) {
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            }

            List<Tickets> result = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }
}
