package project.first.spring.flows.Beer.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.flows.Beer.Exceptions.NotFoundException;
import project.first.spring.flows.Beer.mappers.BeerMapper;
import project.first.spring.flows.Beer.model.BeerStyle;
import project.first.spring.flows.Beer.repositories.BeerRepository;
import project.first.spring.flows.Beer.entities.Beer;
import project.first.spring.flows.Beer.model.BeerDTO;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static project.first.spring.Utilities.Constants.DEFAULT_PAGE;
import static project.first.spring.Utilities.Constants.DEFAULT_PAGE_SIZE;

@Service
@Primary
@AllArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        Page<Beer> beerPage;
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize);

        if (StringUtils.hasText(beerName) && beerStyle != null){
             beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        }
        else if (beerStyle != null){
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        }
        else if (StringUtils.hasText(beerName)) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (Objects.nonNull(showInventory) && !showInventory){
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }
        return beerPage.map(beerMapper::beerToBeerDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber!=null && pageNumber > 0){
            queryPageNumber = pageNumber-1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }
        if(pageSize == null)
            queryPageSize = DEFAULT_PAGE_SIZE;
         else {
            if(pageSize > 1000)
                queryPageSize = 1000;
            else
              queryPageSize = pageSize;
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

         return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    private Page<Beer> listBeersByName(String beerName, Pageable pageable) {
        return beerRepository.findByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    private Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findByBeerStyle(beerStyle, pageable);
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findByBeerNameLikeAndBeerStyleAllIgnoreCase("%" + beerName + "%", beerStyle, pageable);
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
