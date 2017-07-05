package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import entity.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.CustomerService;

import java.util.List;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class CustomerController extends Controller {

    private CustomerService customerService;
    private FormFactory formFactory;

    @Inject
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Inject
    public void setFormFactory(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        Form<Customer> customerForm = formFactory.form(Customer.class).bindFromRequest(request());
        if (customerForm.hasErrors()) {
            return badRequest(customerForm.errorsAsJson());
        }

        customerService.create(customerForm.get());
        return created();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result update(String id) {
        Form<Customer> customerForm = formFactory.form(Customer.class).bindFromRequest(request());
        if (customerForm.hasErrors()) {
            return badRequest(customerForm.errorsAsJson());
        }

        if (customerService.detail(id).getId() != null) {
            Customer updatedCustomer = customerService.update(id, customerForm.get());
            return ok(Json.toJson(updatedCustomer));
        } else {
            return notFound();
        }
    }

    public Result delete(String id) {
        Boolean isDeleted = customerService.delete(id);
        if (isDeleted) {
            return ok();
        } else {
            return notFound();
        }

    }

    public Result detail(String id) {
        Customer customer = customerService.detail(id);
        if (customer.getId() != null) {
            JsonNode customerNode = Json.toJson(customer);
            return ok(customerNode);
        }

        return notFound();

    }

    public Result list() {
        List<Customer> customers = customerService.list();
        return ok(Json.toJson(customers));
    }
}
