package project.first.spring.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.first.spring.entities.Customer;

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
                    .customerName("new name aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").build());
            beerRepository.flush();
        });
    }

}