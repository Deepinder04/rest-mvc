package project.first.spring.flows.Beer.mappers;

import org.mapstruct.Mapper;
import project.first.spring.flows.Beer.entities.Beer;
import project.first.spring.flows.Beer.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
