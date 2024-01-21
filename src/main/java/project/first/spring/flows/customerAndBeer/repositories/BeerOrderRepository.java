package project.first.spring.flows.customerAndBeer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.flows.customerAndBeer.entities.BeerOrder;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
