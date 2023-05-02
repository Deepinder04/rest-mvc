package project.first.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.entities.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
