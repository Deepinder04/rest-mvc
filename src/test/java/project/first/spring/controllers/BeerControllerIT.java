package project.first.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import project.first.spring.Exceptions.NotFoundException;
import project.first.spring.entities.Beer;
import project.first.spring.mappers.BeerMapper;
import project.first.spring.model.BeerDTO;
import project.first.spring.repositories.BeerRepository;
import project.first.spring.services.BeerService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.first.spring.Utils.Constants.BEER_PATH;
import static project.first.spring.Utils.Constants.BEER_PATH_ID;


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

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testBeerByNameAndStyleAndShowInventoryTrue() throws Exception {
        mockMvc.perform(get(BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", "ALE")
                        .queryParam("showInventory","true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(11)))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void testBeerByNameAndStyleAndShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", "ALE")
                        .queryParam("showInventory","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(11)))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.nullValue()));
    }

    @Test
    void testFindBeerByNameAndStyle() throws Exception {
        mockMvc.perform(get(BEER_PATH)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", "ALE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(11)));
    }

    @Test
    void testFindByBeerName() throws Exception {
        mockMvc.perform(get(BEER_PATH)
                .queryParam("beerName","IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(336)));
    }

    @Test
    void testPatchBeerBadName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        Map<String,Object> beerMap = new HashMap<>();
        beerMap.put("beerName","cocaine beer aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        MvcResult mvcResult = mockMvc.perform(patch(BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(4)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

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
        List<BeerDTO> dtos = beerService.listBeers(null, null, false);
        assertThat(dtos.size()).isEqualTo(2413);
    }

    @Rollback
    @Transactional
    @Test
    void testNoEntriesPresent(){
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerService.listBeers(null, null, false);
        assertThat(dtos.size()).isEqualTo(0);
    }
}