package project.first.spring.flows.customerAndBeer.mappers;

import org.mapstruct.Mapper;
import project.first.spring.flows.customerAndBeer.entities.Beer;
import project.first.spring.flows.customerAndBeer.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
