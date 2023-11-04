package project.first.spring.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.first.spring.Exceptions.NotFoundException;
import project.first.spring.model.BeerDTO;
import project.first.spring.model.BeerStyle;
import project.first.spring.services.BeerService;

import java.util.UUID;

import static project.first.spring.Utils.Constants.BEER_PATH;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(BEER_PATH)
public class BeerController {
    private final BeerService beerService;

    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beerDTO){
        beerService.updatePatchById(beerId, beerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){
        if(!beerService.deleteById(beerId)){
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId,@Validated @RequestBody BeerDTO beerDTO){
        beerService.updateById(beerId, beerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping        // check difference between @Valid and @Validated
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beerDTO){
        BeerDTO beerDTOSaved = beerService.saveBeer(beerDTO);

        HttpHeaders header = new HttpHeaders();
        header.add("Location","/api/v1/beer/"+ beerDTOSaved.getId().toString());

        return new ResponseEntity<>(header,HttpStatus.CREATED);
    }

    @GetMapping
    public Page<BeerDTO> listBeers(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize){
        return beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }

    @GetMapping(value = "{beerId}")
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){
        log.debug("Get Beer by Id - in controller");
        return beerService.getById(beerId).orElseThrow(NotFoundException::new);
    }
}



