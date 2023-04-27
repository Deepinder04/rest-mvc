package project.first.spring.services;

import project.first.spring.model.Beer;
import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getById(UUID id);

    Beer saveBeer(Beer beer);

    void updateById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);
}
