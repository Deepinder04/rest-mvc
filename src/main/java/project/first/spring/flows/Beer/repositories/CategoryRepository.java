package project.first.spring.flows.Beer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.flows.Beer.entities.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
