package module;

import com.google.inject.AbstractModule;
import services.CustomerService;
import services.CustomerServiceImpl;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class ModuleService extends AbstractModule {
    @Override
    protected void configure() {
        bind(CustomerService.class).to(CustomerServiceImpl.class);
    }
}
