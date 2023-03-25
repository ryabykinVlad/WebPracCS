package cs.msu.ru.WebPracCMC.model.dao;

import cs.msu.ru.WebPracCMC.model.entity.Tickets;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collection;

public interface TicketsDAO extends GenericDAO<Tickets> {
    Collection<Tickets> getTicketsByFilter(Filter filter);

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
