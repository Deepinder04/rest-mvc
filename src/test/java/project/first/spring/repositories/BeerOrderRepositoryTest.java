package project.first.spring.repositories;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.first.spring.entities.Beer;
import project.first.spring.entities.BeerOrder;
import project.first.spring.entities.Customer;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer customer;

    Beer beer;

    @BeforeEach
    void setUp(){
        customer = customerRepository.findAll().get(0);
        beer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testBeerOrderRepository(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("test customer")
                .customer(customer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);
        System.out.println(savedBeerOrder.getCustomer().getCustomerName());
    }
}