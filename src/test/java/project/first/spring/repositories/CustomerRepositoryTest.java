package project.first.spring.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.first.spring.flows.customerAndBeer.entities.Customer;
import project.first.spring.flows.customerAndBeer.repositories.BeerRepository;
import project.first.spring.flows.customerAndBeer.repositories.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private BeerRepository beerRepository;

    @Test
    void saveCustomerTest(){
        assertThrows(ConstraintViolationException.class, () -> {
            Customer customer = customerRepository.save(Customer.builder()
                    .username("new name aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").build());
            beerRepository.flush();
        });
    }

}