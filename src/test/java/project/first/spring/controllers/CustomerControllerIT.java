package project.first.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
import project.first.spring.entities.Customer;
import project.first.spring.mappers.CustomerMapper;
import project.first.spring.model.CustomerDTO;
import project.first.spring.repositories.CustomerRepository;
import project.first.spring.services.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.first.spring.Utils.Constants.*;
import static project.first.spring.controllers.BeerControllerTest.jwtRequestPostProcessor;

@SpringBootTest
public class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Rollback
    @Transactional
    @Test
    void testPatchCustomerBadName() throws Exception {
        Customer customer = customerRepository.findAll().get(0);
        HashMap<String, String> customerMap = new HashMap<>();
        customerMap.put("customerName", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        MvcResult mvcResult = mockMvc.perform(patch(CUSTOMER_PATH_ID, customer.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testDeleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByIdFound(){
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setUsername("Updated customer");

        ResponseEntity responseEntity = customerController.updateById(customer.getId(),customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        String updatedName = customerRepository.findById(customer.getId()).get().getUsername();
        assertThat(updatedName).isEqualTo(customerDTO.getUsername());
    }

    @Rollback
    @Transactional
    @Test
    void testSaveCustomer() throws JsonProcessingException {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .username("deep")
                .email("deepinder.sidhu@mobikwik.com")
                .build();

        ResponseEntity responseEntity = customerController.addCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] uri = responseEntity.getHeaders().getLocation().toString().split("/");
        Customer customer = customerRepository.findById(UUID.fromString(uri[3])).orElse(null);
        assertThat(customer).isNotNull();
    }

    @Test
    void testGetCustomerById(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotPresent(){
        assertThrows(NotFoundException.class, () -> {
            customerController.getById(UUID.randomUUID());
        });
    }

    @Test
    void testGetCustomerListById(){
        List<CustomerDTO> customers = customerService.customerList();
        assertThat(customers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testNotEntriesPresent(){
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(0);
    }
}
