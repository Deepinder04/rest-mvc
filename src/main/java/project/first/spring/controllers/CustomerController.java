package project.first.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.first.spring.model.CustomerDTO;
import project.first.spring.services.CustomerService;

import java.util.List;
import java.util.UUID;

import static project.first.spring.Utils.Constants.CUSTOMER_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(CUSTOMER_PATH)
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("{customerId}")
    public ResponseEntity patchCustomerById(@PathVariable UUID customerId,@RequestBody CustomerDTO customerDTO){

        customerService.patchCustomerById(customerId, customerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId){
        customerService.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId,@RequestBody CustomerDTO customerDTO){

        customerService.updateById(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","api/v1/customer/"+ savedCustomerDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> listCustomers(){
        return customerService.customerList();
    }

    @RequestMapping(value = "{customerId}",method = RequestMethod.GET)
    public CustomerDTO getById(@PathVariable("customerId")UUID customerId){
        return customerService.getById(customerId);
    }
}
