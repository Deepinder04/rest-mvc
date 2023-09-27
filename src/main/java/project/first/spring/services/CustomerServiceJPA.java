package project.first.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import project.first.spring.mappers.CustomerMapper;
import project.first.spring.model.CustomerDTO;
import project.first.spring.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> customerList() {
        return null;
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {

    }

    @Override
    public void deleteById(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {

    }
}
