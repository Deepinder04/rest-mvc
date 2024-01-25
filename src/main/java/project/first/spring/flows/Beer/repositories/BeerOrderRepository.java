package project.first.spring.flows.Beer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.flows.Beer.entities.BeerOrder;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
