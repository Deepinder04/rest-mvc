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
import org.springframework.test.web.servlet.MvcResult;
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
import static project.first.spring.Utils.Constants.BEER_PATH_ID;

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
    void testUpdateBeerValidationFailure() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        beerDTO.setBeerName("");

        MvcResult mvcResult = mockMvc.perform(put(BEER_PATH_ID,beerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();
        given(beerService.saveBeer(any())).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0));

        MvcResult mvcResult = mockMvc.perform(post(BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(4)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    void testPatchBeer() throws Exception {
        BeerDTO mockedBeerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        Map<String,Object> beerMap = new HashMap<>();
        beerMap.put("beerName","new name");

        mockMvc.perform(patch(BEER_PATH_ID, mockedBeerDTO.getId())
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
        BeerDTO mockedBeerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        given(beerService.deleteById(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(BEER_PATH_ID, mockedBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(mockedBeerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO mockedBeerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);

        mockMvc.perform(put(BEER_PATH_ID, mockedBeerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedBeerDTO)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(any(UUID.class),any(BeerDTO.class));
    }

    @Test
    public void testCreateNewBeer() throws Exception {
        BeerDTO mockBeerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        mockBeerDTO.setId(null);
        mockBeerDTO.setVersion(null);

        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(1));
        mockMvc.perform(post(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockBeerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void listBeerTest() throws Exception {
        given(beerService.listBeers(any(), any(), any(), any(), any())).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25));

        mockMvc.perform(get(BEER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    void testNotFoundException() throws Exception {
        given(beerService.getById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BEER_PATH_ID, UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBeerByIdTest() throws Exception {
        BeerDTO mockBeerDTO = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        given(beerService.getById(mockBeerDTO.getId())).willReturn(Optional.of(mockBeerDTO));

        mockMvc.perform(get(BEER_PATH_ID, mockBeerDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(mockBeerDTO.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(mockBeerDTO.getBeerName())));
    }
}