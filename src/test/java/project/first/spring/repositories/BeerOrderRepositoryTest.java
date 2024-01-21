package project.first.spring.repositories;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.first.spring.flows.customerAndBeer.entities.Beer;
import project.first.spring.flows.customerAndBeer.entities.BeerOrder;
import project.first.spring.flows.customerAndBeer.entities.BeerOrderShipment;
import project.first.spring.flows.customerAndBeer.entities.Customer;
import project.first.spring.flows.customerAndBeer.repositories.BeerOrderRepository;
import project.first.spring.flows.customerAndBeer.repositories.BeerRepository;
import project.first.spring.flows.customerAndBeer.repositories.CustomerRepository;

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
                .beerOrderShipment(BeerOrderShipment.builder()
                        .trackingNumber("1234-ab23").build())
                .customer(customer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        System.out.println(savedBeerOrder.getCustomer().getUsername());
    }
}