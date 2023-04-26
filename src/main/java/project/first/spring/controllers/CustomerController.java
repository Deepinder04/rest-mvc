package project.first.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.first.spring.model.Beer;
import project.first.spring.model.Customer;
import project.first.spring.services.CustomerService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers(){
        return customerService.customerList();
    }

    @RequestMapping(value = "{customerId}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId")UUID customerId){
        return customerService.getCustomerById(customerId);
    }
}
