package project.first.spring.bootstrap;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.first.spring.entities.Beer;
import project.first.spring.entities.Customer;
import project.first.spring.model.BeerDTO;
import project.first.spring.model.BeerStyle;
import project.first.spring.model.CustomerDTO;
import project.first.spring.repositories.BeerRepository;
import project.first.spring.repositories.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    void loadBeerData(){
        Beer beer1 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);
        beerRepository.save(beer3);
    }

    void loadCustomerData(){
        Customer c1 = Customer.builder()
                .customerName("deep")
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c2 = Customer.builder()
                .customerName("vinay")
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c3 = Customer.builder()
                .customerName("ajay")
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerRepository.save(c1);
        customerRepository.save(c2);
        customerRepository.save(c3);
    }

}
