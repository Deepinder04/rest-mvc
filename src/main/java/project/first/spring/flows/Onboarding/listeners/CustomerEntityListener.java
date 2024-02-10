package project.first.spring.flows.Onboarding.listeners;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import project.first.spring.Utilities.encryption.EncryptionService;
import project.first.spring.flows.Onboarding.entities.Customer;

@Component
@Slf4j
public class CustomerEntityListener {

//TODO: ask why autowiring in constructor is required
    private static EncryptionService encryptionService;

    @Autowired
    public CustomerEntityListener(EncryptionService encryptionService) {
        CustomerEntityListener.encryptionService = encryptionService;
    }

    @PrePersist
    @PreUpdate
    void beforeAnyPersist(Customer customer){
        log.info("customer details entity before persist");
        customer.setPassword(StringUtils.hasText(customer.getPassword()) ? encryptionService.encrypt(customer.getPassword()) : null);
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    void afterLoad(Customer customer){
        // can be used as such if different encoder is used
    }
}
