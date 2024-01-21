package project.first.spring.flows.customerAndBeer.services;

import org.springframework.data.domain.Page;
import project.first.spring.flows.customerAndBeer.model.BeerDTO;
import project.first.spring.flows.customerAndBeer.model.BeerStyle;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    Optional<BeerDTO> getById(UUID id);

    BeerDTO saveBeer(BeerDTO beerDTO);

    void updateById(UUID beerId, BeerDTO beerDTO);

    boolean deleteById(UUID beerId);

    void updatePatchById(UUID beerId, BeerDTO beerDTO);
}
