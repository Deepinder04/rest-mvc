package project.first.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.Exceptions.NotFoundException;
import project.first.spring.entities.Customer;
import project.first.spring.mappers.CustomerMapper;
import project.first.spring.model.CustomerDTO;
import project.first.spring.repositories.BeerRepository;
import project.first.spring.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> customerList() {
        return customerRepository.findAll()
                .stream().map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.of(customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO))
                .orElse(null);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {
        // read about AtomicReference
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setCustomerName(customerDTO.getCustomerName());
            customerRepository.save(foundCustomer);
        },NotFoundException::new);
    }

    @Override
    public boolean deleteById(UUID customerId) {
        if(customerRepository.findById(customerId).isPresent()){
            customerRepository.deleteById(customerId);
            return true;
        }
        else
            return false;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(NotFoundException::new);
        if(StringUtils.hasText(customerDTO.getCustomerName()))
            customer.setCustomerName(customerDTO.getCustomerName());
        customerRepository.save(customer);
    }
}
