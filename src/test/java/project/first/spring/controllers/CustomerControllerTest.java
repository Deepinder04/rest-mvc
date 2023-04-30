package project.first.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.first.spring.model.Beer;
import project.first.spring.model.Customer;
import project.first.spring.services.CustomerService;
import project.first.spring.services.CustomerServiceImpl;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CustomerService customerService;

    CustomerService customerServiceImpl;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer mockedCustomer = customerServiceImpl.customerList().get(0);

        mockMvc.perform(delete("/api/v1/customer/"+mockedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(mockedCustomer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer mockedCustomer = customerServiceImpl.customerList().get(0);

        mockMvc.perform(put("/api/v1/customer/"+mockedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerService).updateById(any(UUID.class),any(Customer.class));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer mockedCustomer = customerServiceImpl.customerList().get(0);
        mockedCustomer.setId(null);
        mockedCustomer.setVersion(null);

        given(customerService.saveCustomer(any(Customer.class))).willReturn(customerServiceImpl.customerList().get(1));
        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        Customer mockedCustomer = customerServiceImpl.customerList().get(0);
        given(customerService.getById(mockedCustomer.getId())).willReturn(mockedCustomer);

        mockMvc.perform(get("/api/v1/customer/"+mockedCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName",is(mockedCustomer.getCustomerName())));
    }

    @Test
    public void listCustomersTest() throws Exception {
        given(customerService.customerList()).willReturn(customerServiceImpl.customerList());

        mockMvc.perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }


}
