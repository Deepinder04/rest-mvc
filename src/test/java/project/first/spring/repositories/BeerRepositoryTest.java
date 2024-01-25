package project.first.spring.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.first.spring.bootstrap.BootstrapData;
import project.first.spring.flows.Beer.entities.Beer;
import project.first.spring.flows.Beer.model.BeerStyle;
import project.first.spring.flows.Beer.repositories.BeerRepository;
import project.first.spring.flows.Beer.services.CsvServiceImpl;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, CsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName(){
        Page<Beer> beerList = beerRepository.findByBeerNameIsLikeIgnoreCase("%IPA%", Pageable.ofSize(800));
        assertThat(beerList.getContent().size()).isEqualTo(336);
    }

    @Test
    void testGetBeerListByStyle(){
        Page<Beer> beerList = beerRepository.findByBeerStyle(BeerStyle.ALE, Pageable.ofSize(800));
        assertThat(beerList.getContent().size()).isEqualTo(400);
    }

    @Test
    void testGetBeerByNameAndStyle(){
        Page<Beer> beerList = beerRepository.findByBeerNameLikeAndBeerStyleAllIgnoreCase("%IPA%", BeerStyle.valueOf("ALE"), null);
        assertThat(beerList.getContent().size()).isEqualTo(11);
    }

    @Test
    void saveBeerTest(){
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = beerRepository.save(Beer.builder()
                    .beerName("cocaine beer aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("don't know what upc is")
                    .price(BigDecimal.valueOf(123))
                    .build());
            beerRepository.flush();
        });
    }
}