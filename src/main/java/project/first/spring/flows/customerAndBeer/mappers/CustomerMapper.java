package project.first.spring.flows.customerAndBeer.mappers;

import org.mapstruct.Mapper;
import project.first.spring.flows.customerAndBeer.entities.Customer;
import project.first.spring.flows.customerAndBeer.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
