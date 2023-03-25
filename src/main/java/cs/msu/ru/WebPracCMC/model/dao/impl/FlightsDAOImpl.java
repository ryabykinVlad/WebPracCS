package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.FlightsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
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
public class FlightsDAOImpl extends GenericDAOImpl<Flights>
        implements FlightsDAO {
    public FlightsDAOImpl() {
        super(Flights.class);
    }

    @Override
    public Collection<Flights> getFlightsByFilter(Filter filter) {
        try (Session session = openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Flights> criteriaQuery = builder.createQuery(Flights.class);
            Root<Flights> root = criteriaQuery.from(Flights.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDepartureAirport() != null && !filter.getDepartureAirport().isBlank()) {
                String pattern = getPattern(filter.getDepartureAirport());
                predicates.add(builder.like(root.get("airportName"), pattern));
            }
            if (filter.getArrivalAirport() != null && !filter.getArrivalAirport().isBlank()) {
                String pattern = getPattern(filter.getArrivalAirport());
                predicates.add(builder.like(root.get("airportName"), pattern));
            }
            if (filter.getDepartureDate() != null) {
                var start = filter.getDepartureDate().atStartOfDay();
                var end = start.plusDays(1);
                predicates.add(builder.between(root.get("departure_time"), start, end));
            }

            if (!predicates.isEmpty()) {
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            }

            List<Flights> result = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();
            return result;
        }
    }
    private String getPattern(String string) {
        return "%" + string + "%";
    }
}
