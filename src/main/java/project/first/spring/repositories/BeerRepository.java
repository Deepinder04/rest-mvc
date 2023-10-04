package project.first.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.first.spring.entities.Beer;
import project.first.spring.model.BeerStyle;

import java.util.List;
import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findByBeerNameIsLikeIgnoreCase(String beerName);

    List<Beer> findByBeerStyle(BeerStyle beerStyle);

    List<Beer> findByBeerNameLikeAndBeerStyleAllIgnoreCase(String beerName, BeerStyle beerStyle);
}
