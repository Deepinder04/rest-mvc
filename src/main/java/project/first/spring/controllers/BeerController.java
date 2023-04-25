package project.first.spring.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.first.spring.model.Beer;
import project.first.spring.services.BeerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

    private final BeerService beerService;

    @RequestMapping("api/v1/beerList")
    public List<Beer> beerList(){
        return beerService.getBeerList();
    }
    public Beer getBeerById(UUID id){
        return beerService.getBeerById(id);
    }
}

