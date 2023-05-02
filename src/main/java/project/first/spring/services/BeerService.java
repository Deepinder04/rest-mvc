package project.first.spring.services;

import project.first.spring.model.BeerDTO;
import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    BeerDTO getById(UUID id);

    BeerDTO saveBeer(BeerDTO beerDTO);

    void updateById(UUID beerId, BeerDTO beerDTO);

    void deleteById(UUID beerId);

    void updatePatchById(UUID beerId, BeerDTO beerDTO);
}
