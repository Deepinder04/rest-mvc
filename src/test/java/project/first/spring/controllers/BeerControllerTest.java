package project.first.spring.controllers;

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
import project.first.spring.model.BeerDTO;
import project.first.spring.services.BeerService;
import project.first.spring.services.BeerServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.first.spring.Utils.Constants.BEER_PATH;

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
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    BeerService beerServiceImpl;

    @BeforeEach
    void setUp(){
        beerServiceImpl =new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO mockedBeerDTO = beerServiceImpl.listBeers().get(0);
        Map<String,Object> beerMap = new HashMap<>();
        beerMap.put("beerName","new name");

        mockMvc.perform(patch(BEER_PATH+ mockedBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).updatePatchById(uuidArgumentCaptor.capture(),beerArgumentCaptor.capture());

        assertThat(mockedBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());

    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO mockedBeerDTO = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BEER_PATH+ mockedBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(mockedBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO mockedBeerDTO = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BEER_PATH+ mockedBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedBeerDTO)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class),any(BeerDTO.class));
    }

    @Test
    public void testCreateNewBeer() throws Exception {
        BeerDTO mockBeerDTO = beerServiceImpl.listBeers().get(0);
        mockBeerDTO.setId(null);
        mockBeerDTO.setVersion(null);

        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));
        mockMvc.perform(post(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockBeerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void listBeerTest() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BEER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    public void getBeerByIdTest() throws Exception {

        BeerDTO mockBeerDTO = beerServiceImpl.listBeers().get(0);
        given(beerService.getById(mockBeerDTO.getId())).willReturn(Optional.of(mockBeerDTO));

        mockMvc.perform(get(BEER_PATH+ mockBeerDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(mockBeerDTO.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(mockBeerDTO.getBeerName())));

    }
}