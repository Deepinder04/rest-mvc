package project.first.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.first.spring.entities.Beer;
import project.first.spring.model.BeerStyle;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeerTest(){

       Beer beer = beerRepository.save(Beer.builder()
               .beerName("cocaine beer")
                       .beerStyle(BeerStyle.LAGER)
                       .upc("don't know what upc is")
                       .price(BigDecimal.valueOf(123))
               .build());

       beerRepository.flush();
       assertThat(beer.getBeerName()).isNotNull();
       assertThat(beer.getId()).isNotNull();
    }
}