package dao;

import entity.Customer;
import module.ModuleService;
import org.junit.Assert;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import services.CustomerService;
import services.CustomerServiceImpl;

import java.util.List;

import static play.inject.Bindings.bind;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class CustomerServiceTest {

    public static Application initializeApp() {
        return new GuiceApplicationBuilder()
                .bindings(new ModuleService())
                .bindings(bind(CustomerService.class).to(CustomerServiceImpl.class))
                .build();
    }

    @Test
    public void create() {
        CustomerService customerService = initializeApp().injector().instanceOf(CustomerServiceImpl.class);
        Assert.assertNotNull(customerService);

        Customer customer = new Customer("yuhuuu", "oncom");
        customerService.create(customer);
    }

    @Test
    public void update() {
        CustomerService customerService = initializeApp().injector().instanceOf(CustomerServiceImpl.class);

        String id = "5957d165030ffa6f7945d98a";
        Customer customer = new Customer("update3", "update3");
        Customer updatedCustomer = customerService.update(id, customer);

        Logger.info(updatedCustomer.toString());
        Assert.assertNotNull(updatedCustomer);
    }

    @Test
    public void delete() {
        CustomerService customerService = initializeApp().injector().instanceOf(CustomerServiceImpl.class);
        Assert.assertNotNull(customerService);

        String id = "5957d165030ffa6f7945d98a";
        Assert.assertTrue(customerService.delete(id));
    }

    @Test
    public void detail() {
        CustomerService customerService = initializeApp().injector().instanceOf(CustomerServiceImpl.class);
        Assert.assertNotNull(customerService);

        String id = "5957cfc8030ffa6d346fc81a";
        Customer customer = customerService.detail(id);
        Logger.info(customer.toString());
        Assert.assertNotNull(customer);
    }

    @Test
    public void all() {
        CustomerService customerService = initializeApp().injector().instanceOf(CustomerServiceImpl.class);
        Assert.assertNotNull(customerService);

        List<Customer> customers = customerService.list();
        Assert.assertNotNull(customers);

        customers.forEach(customer -> {
            Logger.info(customer.toString());
        });
    }
}
