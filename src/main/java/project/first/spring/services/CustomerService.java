package project.first.spring.services;

import project.first.spring.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> customerList();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);
}
