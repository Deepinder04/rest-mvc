package project.first.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.first.spring.model.Beer;
import project.first.spring.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID,Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap=new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("koala")
                .beerStyle(BeerStyle.PORTER)
                .price(new BigDecimal("453"))
                .upc("24342352")
                .quantityOnHand(123)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("blue")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("4323"))
                .upc("243423352")
                .quantityOnHand(13)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("white")
                .beerStyle(BeerStyle.GOSE)
                .price(new BigDecimal("4536"))
                .upc("243472352")
                .quantityOnHand(1231)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        beerMap.put(beer1.getId(),beer1);
        beerMap.put(beer2.getId(),beer2);
        beerMap.put(beer3.getId(),beer3);
    }

    @Override
    public List<Beer> getBeerList() {
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Beer getBeerById(UUID id) {
         return beerMap.get(id);
    }
}
