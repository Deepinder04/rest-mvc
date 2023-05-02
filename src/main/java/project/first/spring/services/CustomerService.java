package project.first.spring.services;

import project.first.spring.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> customerList();

    CustomerDTO getById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void updateById(UUID customerId, CustomerDTO customerDTO);

    void deleteById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customerDTO);
}
