package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.TicketsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Tickets;
import org.springframework.stereotype.Repository;

@Repository
public class TicketsDAOImpl extends GenericDAOImpl<Tickets>
        implements TicketsDAO {
    public TicketsDAOImpl() {
        super(Tickets.class);
    }
}
