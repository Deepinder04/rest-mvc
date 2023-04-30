package project.first.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.first.spring.model.Beer;
import project.first.spring.services.BeerService;
import project.first.spring.services.BeerServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.framework;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    BeerService beerServiceImpl;

    @BeforeEach
    void setUp(){
        beerServiceImpl =new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        Beer mockedBeer = beerServiceImpl.listBeers().get(0);
        Map<String,Object> beerMap = new HashMap<>();
        beerMap.put("beerName","new name");

        mockMvc.perform(patch("/api/v1/beer/"+mockedBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).updatePatchById(uuidArgumentCaptor.capture(),beerArgumentCaptor.capture());

        assertThat(mockedBeer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());

    }

    @Test
    void testDeleteBeer() throws Exception {
        Beer mockedBeer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete("/api/v1/beer/"+ mockedBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(mockedBeer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer mockedBeer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put("/api/v1/beer/"+mockedBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedBeer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class),any(Beer.class));
    }

    @Test
    public void testCreateNewBeer() throws Exception {
        Beer mockBeer = beerServiceImpl.listBeers().get(0);
        mockBeer.setId(null);
        mockBeer.setVersion(null);

        given(beerService.saveBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));
        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockBeer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void listBeerTest() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    public void getBeerByIdTest() throws Exception {

        Beer mockBeer = beerServiceImpl.listBeers().get(0);
        given(beerService.getById(mockBeer.getId())).willReturn(mockBeer);

        mockMvc.perform(get("/api/v1/beer/"+mockBeer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(mockBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(mockBeer.getBeerName())));

    }
}