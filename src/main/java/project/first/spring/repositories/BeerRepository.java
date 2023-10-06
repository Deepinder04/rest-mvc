package project.first.spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.first.spring.entities.Beer;
import project.first.spring.model.BeerStyle;

import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);

    Page<Beer> findByBeerStyle(BeerStyle beerStyle, Pageable pageable);

    Page<Beer> findByBeerNameLikeAndBeerStyleAllIgnoreCase(String beerName, BeerStyle beerStyle, Pageable pageable);
}
