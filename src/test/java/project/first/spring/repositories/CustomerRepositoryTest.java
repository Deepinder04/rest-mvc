package project.first.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.first.spring.entities.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomerTest(){
        Customer customer = customerRepository.save(Customer.builder()
                .customerName("new name").build());

        assertThat(customer.getCustomerName()).isNotNull();
    }

}