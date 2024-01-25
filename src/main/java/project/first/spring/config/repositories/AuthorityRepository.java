package project.first.spring.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.config.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
