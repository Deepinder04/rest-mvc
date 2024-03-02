package project.first.spring.flows.beer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.first.spring.flows.beer.entities.BeerOrder;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
