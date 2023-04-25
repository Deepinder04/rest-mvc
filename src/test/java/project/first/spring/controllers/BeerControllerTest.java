package project.first.spring.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class BeerControllerTest {

    @Autowired
    BeerController beerController;

    @Test
    public void getBeerByIdTest()
    {
        System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }
}