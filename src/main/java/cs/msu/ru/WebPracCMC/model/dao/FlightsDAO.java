package cs.msu.ru.WebPracCMC.model.dao;

import cs.msu.ru.WebPracCMC.model.entity.Flights;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collection;

public interface FlightsDAO extends GenericDAO <Flights> {
    Collection<Flights> getFlightsByFilter(Filter filter);

    @Builder
    @Getter
    class Filter {
        private String departureAirport;
        private String arrivalAirport;
        private LocalDate departureDate;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
