package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.FlightsDAO;
import cs.msu.ru.WebPracCMC.model.dao.TicketsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
import cs.msu.ru.WebPracCMC.model.entity.Tickets;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
            session.beginTransaction();
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

            if (!predicates.isEmpty()) {
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            }

            List<Tickets> result = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();

            if (filter.getDepartureDate() != null) {
                var start = filter.getDepartureDate().atStartOfDay();
                var end = start.plusDays(1);
                List<Tickets> filteredResult = new ArrayList<>();
                for (Tickets ticket: result) {
                    LocalDateTime departureTime = ticket.getFlightId().getDepartureTime();
                    if (departureTime.isAfter(start) && departureTime.isBefore(end)) {
                        filteredResult.add(ticket);
                    }
                }
                result = filteredResult;
            }

            return result;
        }
    }

    @Override
    public void buyTicket(TicketsDAO ticketsDAO, FlightsDAO flightsDAO, Flights flight, Clients client, Double purchasePrice, LocalDateTime purchaseTime) {
        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No available seats. Can not buy a ticket");
        }
        Tickets ticket = new Tickets();
        ticket.setFlightId(flight);
        ticket.setClientId(client);
        ticket.setPurchasePrice(purchasePrice);
        ticket.setPurchaseTime(purchaseTime);
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightsDAO.update(flight);
        ticketsDAO.save(ticket);
    }
}
