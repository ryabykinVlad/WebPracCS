package cs.msu.ru.WebPracCMC.model.dao;

import cs.msu.ru.WebPracCMC.model.entity.Clients;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

public interface ClientsDAO extends GenericDAO<Clients> {
    Collection<Clients> getClientsByFilter(Filter filter);

    @Builder
    @Getter
    class Filter {
        private Integer Id;
        private String fullName;
        private String preferredCity;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
