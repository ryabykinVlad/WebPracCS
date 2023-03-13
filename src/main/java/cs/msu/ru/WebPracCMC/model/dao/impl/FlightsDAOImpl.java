package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.FlightsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Flights;
import org.springframework.stereotype.Repository;

@Repository
public class FlightsDAOImpl extends GenericDAOImpl<Flights>
        implements FlightsDAO {
    public FlightsDAOImpl() {
        super(Flights.class);
    }
}
