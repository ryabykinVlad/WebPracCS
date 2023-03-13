package cs.msu.ru.WebPracCMC.model.dao.impl;

import cs.msu.ru.WebPracCMC.model.dao.AirlinesDAO;
import cs.msu.ru.WebPracCMC.model.entity.Airlines;
import org.springframework.stereotype.Repository;

@Repository
public class AirlinesDAOImpl extends GenericDAOImpl<Airlines>
        implements AirlinesDAO {
    public AirlinesDAOImpl() {
        super(Airlines.class);
    }
}
