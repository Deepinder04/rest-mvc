package project.first.spring.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.first.spring.flows.customerAndBeer.entities.Customer;
import project.first.spring.flows.customerAndBeer.repositories.AuthorityRepository;
import project.first.spring.flows.customerAndBeer.repositories.CustomerRepository;


import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    private void loadSecurityData() {
        project.first.spring.flows.customerAndBeer.entities.security.Authority admin = authorityRepository.save(project.first.spring.flows.customerAndBeer.entities.security.Authority.builder().role("ADMIN").build());
        project.first.spring.flows.customerAndBeer.entities.security.Authority userRole = authorityRepository.save(project.first.spring.flows.customerAndBeer.entities.security.Authority.builder().role("USER").build());
        project.first.spring.flows.customerAndBeer.entities.security.Authority customer = authorityRepository.save(project.first.spring.flows.customerAndBeer.entities.security.Authority.builder().role("CUSTOMER").build());

        customerRepository.save(Customer.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .email("deepinder.sidhu@mobikwik.com")
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .authority(admin)
                .build());

        customerRepository.save(Customer.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .email("vinay.patanjali@mobikwik.com")
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .authority(userRole)
                .build());

        customerRepository.save(Customer.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .email("ajay.kumar@mobikwik.com")
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .authority(customer)
                .build());

        log.debug("Users Loaded: " + customerRepository.count());
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
