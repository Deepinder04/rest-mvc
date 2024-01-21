package project.first.spring.flows.customerAndBeer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.flows.customerAndBeer.entities.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
