package cs.msu.ru.WebPracCMC.model.dao;

import cs.msu.ru.WebPracCMC.model.entity.Clients;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
import cs.msu.ru.WebPracCMC.model.entity.Tickets;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface TicketsDAO extends GenericDAO<Tickets> {
    Collection<Tickets> getTicketsByFilter(Filter filter);

    public void buyTicket(TicketsDAO ticketsDAO, FlightsDAO flightsDAO, Flights flight, Clients client, Double purchasePrice, LocalDateTime purchaseTime);

    @Builder
    @Getter
    class Filter {
        private Integer clientId;
        private Integer flightId;
        private LocalDate departureDate;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
