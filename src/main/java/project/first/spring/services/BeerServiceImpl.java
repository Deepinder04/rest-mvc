package project.first.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.model.BeerDTO;
import project.first.spring.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beerDTO1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BeerDTO beerDTO2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BeerDTO beerDTO3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);
    }

    @Override
    public List<BeerDTO> listBeers(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getById(UUID id) {

        log.debug("Get Beer by Id - in service. Id: " + id.toString());

        return Optional.ofNullable(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {

        BeerDTO beerDTOSaved = BeerDTO.builder()
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .price(beerDTO.getPrice())
                .upc(beerDTO.getUpc())
                .id(UUID.randomUUID())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        beerMap.put(beerDTOSaved.getId(), beerDTOSaved);
        return beerDTOSaved;
    }

    @Override
    public void updateById(UUID beerId, BeerDTO beerDTO) {
      BeerDTO updateBeerDTO = beerMap.get(beerId);
      updateBeerDTO.setBeerName(beerDTO.getBeerName());
      updateBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
      updateBeerDTO.setPrice(beerDTO.getPrice());
      updateBeerDTO.setUpc(beerDTO.getUpc());
      updateBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
      beerMap.put(beerId, updateBeerDTO);
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void updatePatchById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO patchBeerDTO = beerMap.get(beerId);

        if(StringUtils.hasText(beerDTO.getBeerName())){
            patchBeerDTO.setBeerName(beerDTO.getBeerName());
        }

        if(beerDTO.getBeerStyle()!=null){
            patchBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
        }

        if(beerDTO.getQuantityOnHand()!=null){
            patchBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
        }

        if(beerDTO.getPrice()!=null){
            patchBeerDTO.setPrice(beerDTO.getPrice());
        }

        if(StringUtils.hasText(beerDTO.getUpc())){
            patchBeerDTO.setUpc(beerDTO.getUpc());
        }
    }
}
