package project.first.spring.services;

import org.springframework.stereotype.Service;
import project.first.spring.model.Customer;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID,Customer> customerMap;


    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer c1 = Customer.builder()
                .customerName("deep")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c2 = Customer.builder()
                .customerName("vinay")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer c3 = Customer.builder()
                .customerName("ajay")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(c1.getId(),c1);
        customerMap.put(c2.getId(),c2);
        customerMap.put(c3.getId(),c3);
    }

    @Override
    public List<Customer> customerList(){
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        Customer savedCustomer = Customer.builder()
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .id(customer.getId())
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(),savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {
        Customer updated = customerMap.get(customerId);
        updated.setCustomerName(customer.getCustomerName());
        customerMap.put(updated.getId(),updated);
    }
}
