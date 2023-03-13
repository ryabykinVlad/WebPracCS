package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.ClientsDAO;
import cs.msu.ru.WebPracCMC.model.entity.Clients;
import org.springframework.stereotype.Repository;

@Repository
public class ClientsDAOImpl extends GenericDAOImpl<Clients>
        implements ClientsDAO {
    public ClientsDAOImpl() {
        super(Clients.class);
    }
}
