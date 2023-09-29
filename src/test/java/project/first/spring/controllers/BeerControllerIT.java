package project.first.spring.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import project.first.spring.Exceptions.NotFoundException;
import project.first.spring.entities.Beer;
import project.first.spring.mappers.BeerMapper;
import project.first.spring.model.BeerDTO;
import project.first.spring.repositories.BeerRepository;
import project.first.spring.services.BeerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerService beerService;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testDeleteByIDNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByIdFound(){
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO testUpdateBeer = beerMapper.beerToBeerDto(beer);
        testUpdateBeer.setId(null);
        testUpdateBeer.setVersion(null);
        testUpdateBeer.setBeerName("Test Updated Beer");

        ResponseEntity responseEntity = beerController.updateBeerById(beer.getId(),testUpdateBeer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        BeerDTO updatedBeer = beerMapper.beerToBeerDto(beerRepository.findById(beer.getId()).orElse(null));
        assertThat(updatedBeer.getBeerName()).isEqualTo(testUpdateBeer.getBeerName());
    }

    @Transactional
    @Rollback
    @Test
    void testSaveBeer(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Test Beer").build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] URISplit = responseEntity.getHeaders().getLocation().getPath().split("/");
        Optional<BeerDTO> fetchedBeer = beerService.getById(UUID.fromString(URISplit[4]));
        assertThat(fetchedBeer).isNotNull();
    }

    @Test
    void testGetBeerById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testGetBeerByIdNotPresent(){
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetBeerListFromDb(){
        List<BeerDTO> dtos = beerService.listBeers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testNoEntriesPresent(){
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerService.listBeers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}