package services;

import entity.Customer;

import java.util.List;

/**
 * Created by satrioaditya on 01/07/17.
 */
public interface CustomerService {
    void create(Customer customer);

    Customer update(String id, Customer customer);

    Boolean delete(String id);

    Customer detail(String id);

    List<Customer> list();
}
