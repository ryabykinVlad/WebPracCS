package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.FlightsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
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
        List<Flights> result = this.getAll().stream().toList();
        if (filter.getDepartureDate() != null) {
            var start = filter.getDepartureDate().atStartOfDay();
            var end = start.plusDays(1);
            List<Flights> filteredResult = new ArrayList<>();
            for (Flights flight: result) {
                LocalDateTime departureTime = flight.getDepartureTime();
                if (departureTime.isAfter(start) && departureTime.isBefore(end)) {
                    filteredResult.add(flight);
                }
            }
            result = filteredResult;
        }

        if (filter.getDepartureAirport() != null && !filter.getDepartureAirport().isBlank()) {
            List<Flights> filteredResult = new ArrayList<>();
            for (Flights flight: result) {
                String departureAirportName = flight.getDepartureAirportId().getAirportName().toLowerCase();
                String departureIataCode = flight.getDepartureAirportId().getIataCode().toLowerCase();
                String departureCityName = flight.getDepartureAirportId().getAirportCity().toLowerCase();
                String departureCountryName = flight.getDepartureAirportId().getAirportCountry().toLowerCase();
                if (departureAirportName.contains(filter.getDepartureAirport().toLowerCase()) ||
                        departureIataCode.contains(filter.getDepartureAirport().toLowerCase()) ||
                        departureCityName.contains(filter.getDepartureAirport().toLowerCase()) ||
                        departureCountryName.contains(filter.getDepartureAirport().toLowerCase())) {
                    filteredResult.add(flight);
                }
            }
            result = filteredResult;
        }
        if (filter.getArrivalAirport() != null && !filter.getArrivalAirport().isBlank()) {
            List<Flights> filteredResult = new ArrayList<>();
            for (Flights flight: result) {
                String arrivalAirportName = flight.getArrivalAirportId().getAirportName().toLowerCase();
                String arrivalIataCode = flight.getArrivalAirportId().getIataCode().toLowerCase();
                String arrivalCityName = flight.getArrivalAirportId().getAirportCity().toLowerCase();
                String arrivalCountryName = flight.getArrivalAirportId().getAirportCountry().toLowerCase();
                if (arrivalAirportName.contains(filter.getArrivalAirport().toLowerCase()) ||
                        arrivalIataCode.contains(filter.getArrivalAirport().toLowerCase()) ||
                        arrivalCityName.contains(filter.getArrivalAirport().toLowerCase()) ||
                        arrivalCountryName.contains(filter.getArrivalAirport().toLowerCase())) {
                    filteredResult.add(flight);
                }
            }
            result = filteredResult;
        }
        return result;
    }
}
