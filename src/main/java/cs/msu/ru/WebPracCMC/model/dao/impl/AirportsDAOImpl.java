package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.AirportsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Airports;
import org.springframework.stereotype.Repository;

@Repository
public class AirportsDAOImpl extends GenericDAOImpl<Airports>
        implements AirportsDAO {
    public AirportsDAOImpl() {
        super(Airports.class);
    }
}
