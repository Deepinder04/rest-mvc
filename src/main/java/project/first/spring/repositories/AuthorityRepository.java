package project.first.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.entities.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
