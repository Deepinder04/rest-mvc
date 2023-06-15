package project.first.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import project.first.spring.mappers.BeerMapper;
import project.first.spring.model.BeerDTO;
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
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public void updateById(UUID beerId, BeerDTO beerDTO) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void updatePatchById(UUID beerId, BeerDTO beerDTO) {

    }
}
