package project.first.spring.flows.customerAndBeer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.flows.customerAndBeer.entities.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
