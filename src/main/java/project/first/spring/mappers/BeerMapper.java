package project.first.spring.mappers;

import org.mapstruct.Mapper;
import project.first.spring.entities.Beer;
import project.first.spring.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
