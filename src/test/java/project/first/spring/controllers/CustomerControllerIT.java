package project.first.spring.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.first.spring.entities.Customer;
import project.first.spring.repositories.CustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testNotEntriesPresent(){
        customerRepository.deleteAll();
        List<Customer> dtos = customerRepository.findAll();
        assertThat(dtos.size()).isEqualTo(0);
    }
}
