package project.first.spring.flows.Onboarding.services;

import project.first.spring.flows.Onboarding.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> customerList();

    Optional<CustomerDTO> getById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    void updateById(UUID customerId, CustomerDTO customerDTO);

    boolean deleteById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customerDTO);
}
