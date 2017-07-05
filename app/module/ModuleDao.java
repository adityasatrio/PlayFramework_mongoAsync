package module;

import com.google.inject.AbstractModule;
import dao.CustomerDao;
import dao.CustomerDaoImpl;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class ModuleDao extends AbstractModule {
    @Override
    protected void configure() {
        bind(CustomerDao.class).to(CustomerDaoImpl.class);
    }
}
