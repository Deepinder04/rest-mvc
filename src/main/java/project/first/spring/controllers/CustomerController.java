package project.first.spring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.first.spring.model.Beer;
import project.first.spring.model.Customer;
import project.first.spring.services.CustomerService;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("{customerId}")
    public ResponseEntity patchCustomerById(@PathVariable UUID customerId,@RequestBody Customer customer){

        customerService.patchCustomerById(customerId,customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customerService.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId,@RequestBody Customer customer){

        customerService.updateById(customerId,customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","api/v1/customer/"+savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers(){
        return customerService.customerList();
    }

    @RequestMapping(value = "{customerId}",method = RequestMethod.GET)
    public Customer getById(@PathVariable("customerId")UUID customerId){
        return customerService.getById(customerId);
    }
}
