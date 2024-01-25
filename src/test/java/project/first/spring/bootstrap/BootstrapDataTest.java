package project.first.spring.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import project.first.spring.flows.Beer.repositories.BeerRepository;
import project.first.spring.flows.Onboarding.repositories.CustomerRepository;
import project.first.spring.flows.Beer.services.CsvService;
import project.first.spring.flows.Beer.services.CsvServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CsvService csvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, csvService);
    }

    @Test
    void bootstrapDataTest() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}