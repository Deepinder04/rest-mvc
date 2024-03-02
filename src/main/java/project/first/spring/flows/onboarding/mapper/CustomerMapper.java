package project.first.spring.flows.onboarding.mapper;

import org.mapstruct.Mapper;
import project.first.spring.flows.onboarding.entities.Customer;
import project.first.spring.flows.onboarding.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
