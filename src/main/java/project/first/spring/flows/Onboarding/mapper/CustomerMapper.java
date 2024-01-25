package project.first.spring.flows.Onboarding.mapper;

import org.mapstruct.Mapper;
import project.first.spring.flows.Onboarding.entities.Customer;
import project.first.spring.flows.Onboarding.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
