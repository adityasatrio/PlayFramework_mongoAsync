package dao;

import entity.Customer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by satrioaditya on 01/07/17.
 */
public interface CustomerDao {
    void save(Customer customer);

    Customer update(String id, Customer customer) throws ExecutionException, InterruptedException, IOException;

    Boolean delete(String id) throws ExecutionException, InterruptedException;

    Customer findById(String id) throws ExecutionException, InterruptedException, IOException;

    List<Customer> all() throws ExecutionException, InterruptedException;

}
