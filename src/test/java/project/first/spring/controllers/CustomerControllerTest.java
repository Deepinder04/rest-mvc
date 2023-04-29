package project.first.spring.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.first.spring.model.Customer;
import project.first.spring.services.CustomerService;
import project.first.spring.services.CustomerServiceImpl;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
