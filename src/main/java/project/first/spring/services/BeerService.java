package project.first.spring.services;

import project.first.spring.model.Beer;
import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> getBeerList();

    public Beer getBeerById(UUID id);
}
