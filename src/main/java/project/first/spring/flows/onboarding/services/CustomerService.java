package project.first.spring.flows.onboarding.services;

import project.first.spring.flows.onboarding.model.CustomerDTO;
import project.first.spring.flows.onboarding.model.LoginDto;
import project.first.spring.flows.onboarding.model.SignUpDto;

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

    String login(LoginDto loginDto);

    String signUp(SignUpDto signUpDto);
}
