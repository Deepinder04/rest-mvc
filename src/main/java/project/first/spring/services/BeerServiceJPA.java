package project.first.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.Exceptions.NotFoundException;
import project.first.spring.entities.Beer;
import project.first.spring.mappers.BeerMapper;
import project.first.spring.model.BeerDTO;
import project.first.spring.model.BeerStyle;
import project.first.spring.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers(String beerName, String beerStyle) {
        List<Beer> beerList;

        if (StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)){
             beerList = listBeersByNameAndStyle(beerName,beerStyle);
        }
        else if (StringUtils.hasText(beerStyle)){
            beerList = listBeersByStyle(beerStyle);
        }
        else if (StringUtils.hasText(beerName)) {
            beerList = listBeersByName(beerName);
        } else {
            beerList = beerRepository.findAll();
        }

        return beerList.stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    private List<Beer> listBeersByName(String beerName) {
        return beerRepository.findByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
    }

    private List<Beer> listBeersByStyle(String beerStyle) {
        return beerRepository.findByBeerStyle(BeerStyle.valueOf(beerStyle));
    }

    private List<Beer> listBeersByNameAndStyle(String beerName, String beerStyle) {
        return beerRepository.findByBeerNameLikeAndBeerStyleAllIgnoreCase("%" + beerName + "%", BeerStyle.valueOf(beerStyle));
    }

    @Override
    public Optional<BeerDTO> getById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public void updateById(UUID beerId, BeerDTO beerDTO) {
        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setPrice(beerDTO.getPrice());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            foundBeer.setUpc(beerDTO.getUpc());
            beerRepository.save(foundBeer);
        }, NotFoundException::new);
    }

    @Override
    public boolean deleteById(UUID beerId) {
        if (beerRepository.findById(beerId).isPresent()){
            beerRepository.deleteById(beerId);
            return true;
        }
        else
            return false;
    }

    @Override
    public void updatePatchById(UUID beerId, BeerDTO beerDTO) {
        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beerDTO.getBeerName())){
                foundBeer.setBeerName(beerDTO.getBeerName());
            }
            if (beerDTO.getBeerStyle() != null){
                foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            }
            if (StringUtils.hasText(beerDTO.getUpc())){
                foundBeer.setUpc(beerDTO.getUpc());
            }
            if (beerDTO.getPrice() != null){
                foundBeer.setPrice(beerDTO.getPrice());
            }
            if (beerDTO.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            beerRepository.save(beerMapper.beerDtoToBeer(beerDTO));
        }, NotFoundException::new);
    }

}
