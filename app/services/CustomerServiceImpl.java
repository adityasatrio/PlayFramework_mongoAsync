package services;

import com.google.inject.Inject;
import entity.Customer;
import play.Logger;
import dao.CustomerDao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    @Inject
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void create(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public Customer update(String id, Customer customer) {
        try {

            return customerDao.update(id, customer);

        } catch (ExecutionException | InterruptedException e) {
            Logger.error("Asynchronous failed to update # " + e.getMessage(), e.getCause());
            throw new RuntimeException("Asynchronous failed to update # " + e.getMessage(), e.getCause());

        } catch (IOException e) {
            Logger.error("succesfully updated with failed parsing return value # " + e.getMessage(), e.getCause());
            throw new RuntimeException("succesfully updated with failed parsing return value # " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public Boolean delete(String id) {
        try {

            if (customerDao.findById(id).getId() != null) {
                return customerDao.delete(id);
            } else {
                return false;
            }

        } catch (ExecutionException | InterruptedException e) {
            Logger.error("Asynchronous failed to delete # " + e.getMessage(), e.getCause());
            throw new RuntimeException("Asynchronous failed to delete # " + e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Customer detail(String id) {
        try {
            return customerDao.findById(id);

        } catch (ExecutionException | InterruptedException e) {
            Logger.error("Asynchronous failed to update # " + e.getMessage(), e.getCause());
            throw new RuntimeException("Asynchronous failed to update # " + e.getMessage(), e.getCause());

        } catch (IOException e) {
            Logger.error("succesfully updated with failed parsing return value # " + e.getMessage(), e.getCause());
            throw new RuntimeException("succesfully updated with failed parsing return value # " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Customer> list() {
        try {
            return customerDao.all();

        } catch (ExecutionException | InterruptedException e) {
            Logger.error("Asynchronous failed to update # " + e.getMessage(), e.getCause());
            throw new RuntimeException("Asynchronous failed to update # " + e.getMessage(), e.getCause());
        }
    }
}