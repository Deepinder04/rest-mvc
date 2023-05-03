package project.first.spring.mappers;

import org.mapstruct.Mapper;
import project.first.spring.entities.Customer;
import project.first.spring.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
