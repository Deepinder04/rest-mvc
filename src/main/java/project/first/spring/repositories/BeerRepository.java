package project.first.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.entities.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
