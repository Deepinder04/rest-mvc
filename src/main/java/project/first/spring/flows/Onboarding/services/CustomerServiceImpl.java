package project.first.spring.flows.Onboarding.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.flows.Onboarding.model.CustomerDTO;
import project.first.spring.flows.Onboarding.model.LoginDto;
import project.first.spring.flows.Onboarding.model.SignUpDto;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;


    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO c1 = CustomerDTO.builder()
                .username("deep")
                .email("deepinder.sidhu@mobikwik.com")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO c2 = CustomerDTO.builder()
                .username("vinay")
                .email("vinay.patanjali@mobikwik.com")
                .id(UUID.randomUUID())
                .version(1)
                .createdAt(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO c3 = CustomerDTO.builder()
                .username("ajay")
                .email("ajay.kumar@mobikwik.com")
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
                .username(customerDTO.getUsername())
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
        if(StringUtils.hasText(customerDTO.getUsername())){
            customerDTOPatch.setUsername(customerDTO.getUsername());
        }
    }

    @Override
    public String login(LoginDto loginDto) {
        return null;
    }

    @Override
    public String signUp(SignUpDto signUpDto) {
        return null;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO updated = customerMap.get(customerId);
        updated.setUsername(customerDTO.getUsername());
        customerMap.put(updated.getId(),updated);
    }
}
