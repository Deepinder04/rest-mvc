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
import project.first.spring.model.CustomerDTO;
import project.first.spring.services.CustomerService;
import project.first.spring.services.CustomerServiceImpl;

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
import static project.first.spring.Utils.Constants.CUSTOMER_PATH;
import static project.first.spring.Utils.Constants.CUSTOMER_PATH_ID;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerService customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testCreateCustomerEmptyCustomerName() throws Exception {
        CustomerDTO customer = customerServiceImpl.customerList().get(0);
        customer.setCustomerName("");
        customer.setEmail("deepinder.sidhu@mobikwik.com");

        MvcResult mvcResult = mockMvc.perform(post(CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO mockedCustomerDTO = customerServiceImpl.customerList().get(0);
        Map<String,Object> customerMap = new HashMap<>();
        customerMap.put("customerName","new name");

        mockMvc.perform(patch(CUSTOMER_PATH_ID, mockedCustomerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerMap))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),customerArgumentCaptor.capture());
        assertThat(mockedCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO mockedCustomerDTO = customerServiceImpl.customerList().get(0);
        given(customerService.deleteById(mockedCustomerDTO.getId())).willReturn(true);

        mockMvc.perform(delete(CUSTOMER_PATH_ID, mockedCustomerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(mockedCustomerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO mockedCustomerDTO = customerServiceImpl.customerList().get(0);

        mockMvc.perform(put(CUSTOMER_PATH_ID, mockedCustomerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedCustomerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerService).updateById(any(UUID.class),any(CustomerDTO.class));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerDTO mockedCustomerDTO = customerServiceImpl.customerList().get(0);
        mockedCustomerDTO.setId(null);
        mockedCustomerDTO.setVersion(null);

        given(customerService.saveCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.customerList().get(1));
        mockMvc.perform(post(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedCustomerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        CustomerDTO mockedCustomerDTO = customerServiceImpl.customerList().get(0);
        given(customerService.getById(mockedCustomerDTO.getId())).willReturn(Optional.of(mockedCustomerDTO));

        mockMvc.perform(get(CUSTOMER_PATH_ID, mockedCustomerDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName",is(mockedCustomerDTO.getCustomerName())));
    }

    @Test
    public void listCustomersTest() throws Exception {
        given(customerService.customerList()).willReturn(customerServiceImpl.customerList());

        mockMvc.perform(get(CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void testNotFoundException() throws Exception {
        given(customerService.getById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CUSTOMER_PATH_ID, UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
