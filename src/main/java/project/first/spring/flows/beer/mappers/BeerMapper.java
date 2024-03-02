package project.first.spring.flows.beer.mappers;

import org.mapstruct.Mapper;
import project.first.spring.flows.beer.entities.Beer;
import project.first.spring.flows.beer.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
