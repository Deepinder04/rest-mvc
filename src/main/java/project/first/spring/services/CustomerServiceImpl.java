package project.first.spring.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;


    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO c1 = CustomerDTO.builder()
                .customerName("deep")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO c2 = CustomerDTO.builder()
                .customerName("vinay")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO c3 = CustomerDTO.builder()
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
    public List<CustomerDTO> customerList(){
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        CustomerDTO savedCustomerDTO = CustomerDTO.builder()
                .customerName(customerDTO.getCustomerName())
                .version(customerDTO.getVersion())
                .id(customerDTO.getId())
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomerDTO.getId(), savedCustomerDTO);
        return savedCustomerDTO;
    }

    @Override
    public boolean deleteById(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {

        CustomerDTO customerDTOPatch = customerMap.get(customerId);
        if(StringUtils.hasText(customerDTO.getCustomerName())){
            customerDTOPatch.setCustomerName(customerDTO.getCustomerName());
        }
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO updated = customerMap.get(customerId);
        updated.setCustomerName(customerDTO.getCustomerName());
        customerMap.put(updated.getId(),updated);
    }
}
