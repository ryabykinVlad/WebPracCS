package cs.msu.ru.WebPracCMC.controllers;

import cs.msu.ru.WebPracCMC.model.dao.*;
import cs.msu.ru.WebPracCMC.model.dao.impl.*;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
import cs.msu.ru.WebPracCMC.model.entity.Tickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class TicketsController {
    @Autowired
    private final FlightsDAO flightsDAO = new FlightsDAOImpl();
    @Autowired
    private final ClientsDAO clientsDAO = new ClientsDAOImpl();
    @Autowired
    private final TicketsDAO ticketsDAO = new TicketsDAOImpl();

    @GetMapping(value = { "/tickets", "/tickets/"})
    public String tickets_page(Model model,
                               @RequestParam(name = "clientId", required = false) Integer clientId,
                               @RequestParam(name = "flightId", required = false) Integer flightId,
                               @RequestParam(name = "departureDate", required = false) String departureDate) {
        Collection<Tickets> tickets = ticketsDAO.getAll();
        var filterBuilder = TicketsDAO.getFilterBuilder();
        boolean flag = false;
        if (clientId != null) {
            filterBuilder.clientId(clientId);
            flag = true;
        }
        if (flightId != null) {
            filterBuilder.flightId(flightId);
            flag = true;
        }
        if (departureDate != null && !departureDate.isEmpty()) {
            filterBuilder.departureDate(LocalDate.parse(departureDate));
            flag = true;
        }
        if (flag) {
            tickets = ticketsDAO.getTicketsByFilter(filterBuilder.build());
        }
        model.addAttribute("tickets", tickets);
        model.addAttribute("action", "/tickets");
        return "tickets";
    }

    @GetMapping("/tickets/{id}")
    public String ticket_page(@PathVariable(name = "id") Integer ticketId, Model model) {
        Tickets ticket = ticketsDAO.getById(ticketId);
        model.addAttribute("ticket", ticket);
        return "ticket";
    }

    @GetMapping("/tickets/add/")
    public String add_ticket_page(Model model) {
        model.addAttribute("add", true);
        model.addAttribute("edit", false);
        model.addAttribute("action", "/tickets/add/");
        return "ticket_add";
    }

    @PostMapping("/tickets/add/")
    public String add_ticket(
            @RequestParam(name = "clientId") Integer clientId,
            @RequestParam(name = "flightId") Integer flightId,
            @RequestParam(name = "purchasePrice") Double purchasePrice,
            @RequestParam(name = "purchaseTime") String purchaseTime
    ) {
        Flights flight = flightsDAO.getById(flightId);
        Clients client = clientsDAO.getById(clientId);
        ticketsDAO.buyTicket(ticketsDAO, flightsDAO, flight, client, purchasePrice, LocalDateTime.parse(purchaseTime));
        return "redirect:/tickets";
    }

    @GetMapping("/tickets/buy/{id}")
    public String buy_ticket_page(@PathVariable(name = "id") Integer flightId, Model model) {
        Flights flight = flightsDAO.getById(flightId);
        model.addAttribute("flight", flight);
        model.addAttribute("add", true);
        model.addAttribute("edit", false);
        model.addAttribute("action", "/tickets/buy/" + flight.getFlightId().toString());
        return "ticket_buy";
    }

    @PostMapping("/tickets/buy/{id}")
    public String buy_ticket(
            @RequestParam(name = "clientId") Integer clientId,
            @PathVariable(name = "id") Integer flightId,
            Model model
    ) {
        Flights flight = flightsDAO.getById(flightId);
        Clients client = clientsDAO.getById(clientId);
        ticketsDAO.buyTicket(ticketsDAO, flightsDAO, flight, client, flight.getCurrentPrice(), LocalDateTime.now());
        return "redirect:/tickets";
    }

    @DeleteMapping("/tickets/delete/{id}")
    public String delete_ticket(@PathVariable(name = "id") Integer ticketId) {
        ticketsDAO.delete(ticketsDAO.getById(ticketId));
        return "redirect:/tickets";
    }
}
