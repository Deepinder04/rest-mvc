package project.first.spring.repositories;

import jakarta.validation.ConstraintViolationException;
import org.apache.catalina.startup.Bootstrap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import project.first.spring.bootstrap.BootstrapData;
import project.first.spring.entities.Beer;
import project.first.spring.model.BeerStyle;
import project.first.spring.services.CsvServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, CsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName(){
        List<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
        assertThat(beerList.size()).isEqualTo(336);
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