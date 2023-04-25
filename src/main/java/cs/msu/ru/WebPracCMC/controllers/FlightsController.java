package cs.msu.ru.WebPracCMC.controllers;

import cs.msu.ru.WebPracCMC.model.dao.AirlinesDAO;
import cs.msu.ru.WebPracCMC.model.dao.AirportsDAO;
import cs.msu.ru.WebPracCMC.model.dao.FlightsDAO;
import cs.msu.ru.WebPracCMC.model.dao.impl.AirlinesDAOImpl;
import cs.msu.ru.WebPracCMC.model.dao.impl.AirportsDAOImpl;
import cs.msu.ru.WebPracCMC.model.dao.impl.FlightsDAOImpl;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class FlightsController {
    @Autowired
    private final FlightsDAO flightsDAO = new FlightsDAOImpl();
    @Autowired
    private final AirportsDAO airportsDAO = new AirportsDAOImpl();
    @Autowired
    private final AirlinesDAO airlinesDAO = new AirlinesDAOImpl();

    @GetMapping(value = {"/", "/flights"})
    public String flights_page(Model model,
                               @RequestParam(name = "departureAirport", required = false) String departureAirport,
                               @RequestParam(name = "arrivalAirport", required = false) String arrivalAirport,
                               @RequestParam(name = "departureDate", required = false) String departureDate) {
        Collection<Flights> flights = flightsDAO.getAll();
        var filterBuilder = FlightsDAO.getFilterBuilder();
        boolean flag = false;
        if (departureAirport != null && !departureAirport.isEmpty()) {
            filterBuilder.departureAirport(departureAirport.toLowerCase());
            flag = true;
        }
        if (arrivalAirport != null && !arrivalAirport.isEmpty()) {
            filterBuilder.arrivalAirport(arrivalAirport.toLowerCase());
            flag = true;
        }
        if (departureDate != null && !departureDate.isEmpty()) {
            filterBuilder.departureDate(LocalDate.parse(departureDate));
            flag = true;
        }
        if (flag) {
            flights = flightsDAO.getFlightsByFilter(filterBuilder.build());
        }
        model.addAttribute("flights", flights);
        model.addAttribute("action", "/flights");
        return "flights";
    }

    @GetMapping("/flights/{id}")
    public String flight_page(@PathVariable(name = "id") Integer flightId, Model model) {
        Flights flight = flightsDAO.getById(flightId);
        model.addAttribute("flight", flight);
        return "flight";
    }

    @GetMapping("/flights/add/")
    public String add_flight_page(Model model) {
        model.addAttribute("add", true);
        model.addAttribute("edit", false);
        model.addAttribute("action", "/flights/add/");
        return "flight_add";
    }

    @PostMapping("/flights/add/")
    public String add_flight(
            @RequestParam(name = "airlineId") Integer airlineId,
            @RequestParam(name = "departureAirportId") Integer departureAirportId,
            @RequestParam(name = "arrivalAirportId") Integer arrivalAirportId,
            @RequestParam(name = "departureTime") String departureTime,
            @RequestParam(name = "arrivalTime") String arrivalTime,
            @RequestParam(name = "currentPrice") Double currentPrice,
            @RequestParam(name = "availableSeats") Integer availableSeats,
            @RequestParam(name = "totalSeats") Integer totalSeats
    ) {
        Flights flight = new Flights();
        flight.setAirlineId(airlinesDAO.getById(airlineId));
        flight.setDepartureAirportId(airportsDAO.getById(departureAirportId));
        flight.setArrivalAirportId(airportsDAO.getById(arrivalAirportId));
        flight.setDepartureTime(LocalDateTime.parse(departureTime));
        flight.setArrivalTime(LocalDateTime.parse(arrivalTime));
        flight.setCurrentPrice(currentPrice);
        flight.setAvailableSeats(availableSeats);
        flight.setTotalSeats(totalSeats);

        flightsDAO.save(flight);
        return "redirect:/flights";
    }

    @GetMapping("/flights/edit/{id}")
    public String edit_flight_page(@PathVariable(name = "id") Integer flightId, Model model) {
        Flights flight = flightsDAO.getById(flightId);
        model.addAttribute("flight", flight);
        model.addAttribute("action", "/flights/edit/" + flightId.toString());
        return "flight_edit";
    }

    @PostMapping("/flights/edit/{id}")
    public String edit_flight(
            @RequestParam(name = "airlineId", required = false) Integer airlineId,
            @RequestParam(name = "departureAirportId", required = false) Integer departureAirportId,
            @RequestParam(name = "arrivalAirportId", required = false) Integer arrivalAirportId,
            @RequestParam(name = "departureTime", required = false) String departureTime,
            @RequestParam(name = "arrivalTime", required = false) String arrivalTime,
            @RequestParam(name = "currentPrice", required = false) Double currentPrice,
            @RequestParam(name = "availableSeats", required = false) Integer availableSeats,
            @RequestParam(name = "totalSeats", required = false) Integer totalSeats,
            @PathVariable(name = "id") Integer flightId
    ) {
        Flights flight = flightsDAO.getById(flightId);
        if (airlineId != null) {
            flight.setAirlineId(airlinesDAO.getById(airlineId));
        }
        if (departureAirportId != null) {
            flight.setDepartureAirportId(airportsDAO.getById(departureAirportId));
        }
        if (arrivalAirportId != null) {
            flight.setArrivalAirportId(airportsDAO.getById(arrivalAirportId));
        }
        if (departureTime != null && !departureTime.isEmpty()) {
            flight.setDepartureTime(LocalDateTime.parse(departureTime));
        }
        if (arrivalTime != null && !arrivalTime.isEmpty()) {
            flight.setArrivalTime(LocalDateTime.parse(arrivalTime));
        }
        if (currentPrice != null) {
            flight.setCurrentPrice(currentPrice);
        }
        if (availableSeats != null) {
            flight.setAvailableSeats(availableSeats);
        }
        if (totalSeats != null) {
            flight.setTotalSeats(totalSeats);
        }
        flightsDAO.update(flight);

        return "redirect:/flights";
    }

    @DeleteMapping("/flights/delete/{id}")
    public String delete_flight(@PathVariable(name = "id") Integer flightId) {
        flightsDAO.delete(flightsDAO.getById(flightId));
        return "redirect:/flights";
    }
}
