package project.first.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.entities.BeerOrder;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
