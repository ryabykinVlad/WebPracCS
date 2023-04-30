package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.ClientsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ClientsDAOImpl extends GenericDAOImpl<Clients>
        implements ClientsDAO {
    public ClientsDAOImpl() {
        super(Clients.class);
    }

    @Override
    public Collection<Clients> getClientsByFilter(Filter filter) {
        List<Clients> result = this.getAll().stream().toList();
        if (filter.getId() != null) {
            List<Clients> filteredResult = new ArrayList<>();
            for (Clients client: result) {
                if (client.getClientId() == filter.getId()) {
                    filteredResult.add(client);
                }
            }
            result = filteredResult;
        }
        if (filter.getFullName() != null && !filter.getFullName().isBlank()) {
            List<Clients> filteredResult = new ArrayList<>();
            for (Clients client: result) {
                String fullName = client.getFullName().toLowerCase();
                if (fullName.contains(filter.getFullName().toLowerCase())) {
                    filteredResult.add(client);
                }
            }
            result = filteredResult;
        }
        if (filter.getPreferredCity() != null && !filter.getPreferredCity().isBlank()) {
            List<Clients> filteredResult = new ArrayList<>();
            for (Clients client: result) {
                if (client.getPreferredCity() == null) {
                    continue;
                }
                String preferredCity = client.getPreferredCity().toLowerCase();
                if (preferredCity.contains(filter.getPreferredCity().toLowerCase())) {
                    filteredResult.add(client);
                }
            }
            result = filteredResult;
        }
        return result;
    }
}
