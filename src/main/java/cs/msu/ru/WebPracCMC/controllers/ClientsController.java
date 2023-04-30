package cs.msu.ru.WebPracCMC.controllers;

import cs.msu.ru.WebPracCMC.model.dao.ClientsDAO;
import cs.msu.ru.WebPracCMC.model.dao.impl.ClientsDAOImpl;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class ClientsController {
    @Autowired
    private final ClientsDAO clientsDAO = new ClientsDAOImpl();

    @GetMapping(value = {"/clients", "/clients/"})
    public String clients_page(Model model,
            @RequestParam(name = "clientId", required = false) Integer clientId,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "preferredCity", required = false) String preferredCity) {
        Collection<Clients> clients = clientsDAO.getAll();
        var filterBuilder = ClientsDAO.getFilterBuilder();
        boolean flag = false;
        if (clientId != null) {
            filterBuilder.Id(clientId);
            flag = true;
        }
        if (fullName != null && !fullName.isEmpty()) {
            filterBuilder.fullName(fullName.toLowerCase());
            flag = true;
        }
        if (preferredCity != null && !preferredCity.isEmpty()) {
            filterBuilder.preferredCity(preferredCity.toLowerCase());
            flag = true;
        }
        if (flag) {
            clients = clientsDAO.getClientsByFilter(filterBuilder.build());
        }
        model.addAttribute("clients", clients);
        model.addAttribute("action", "/clients");
        return "clients";
    }

    @GetMapping("/clients/{id}")
    public String client_page(@PathVariable(name = "id") Integer clientId, Model model) {
        Clients client = clientsDAO.getById(clientId);
        model.addAttribute("client", client);
        return "client";
    }

    @GetMapping("/clients/add/")
    public String add_client_page(Model model) {
        model.addAttribute("add", true);
        model.addAttribute("edit", false);
        model.addAttribute("action", "/clients/add/");
        return "client_add";
    }

    @PostMapping("/clients/add/")
    public String add_client(
            @RequestParam(name = "fullName") String fullName,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "preferredCity") String preferredCity
    ) {
        Clients client = new Clients();
        client.setFullName(fullName);
        client.setPhone(phone);
        client.setEmail(email);
        client.setPreferredCity(preferredCity);
        clientsDAO.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String edit_client_page(@PathVariable(name = "id") Integer clientId, Model model) {
        Clients client = clientsDAO.getById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("action", "/clients/edit/" + clientId.toString());
        return "client_edit";
    }

    @PostMapping("/clients/edit/{id}")
    public String edit_client(
            @RequestParam(name = "fullName") String fullName,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "preferredCity") String preferredCity,
            @PathVariable(name = "id") Integer clientId
    ) {
        Clients client = clientsDAO.getById(clientId);
        if (fullName != null && !fullName.isEmpty()) {
            client.setFullName(fullName);
        }
        if (phone != null && !phone.isEmpty()) {
            client.setPhone(phone);
        }
        if (email != null && !email.isEmpty()) {
            client.setEmail(email);
        }
        if (preferredCity != null && !preferredCity.isEmpty()) {
            client.setPreferredCity(preferredCity);
        }
        clientsDAO.update(client);

        return "redirect:/clients";
    }

    @DeleteMapping("/clients/delete/{id}")
    public String delete_client(@PathVariable(name = "id") Integer clientId) {
        clientsDAO.delete(clientsDAO.getById(clientId));
        return "redirect:/clients";
    }
}
