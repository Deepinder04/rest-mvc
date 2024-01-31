package project.first.spring.flows.Onboarding.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.first.spring.Utilities.Utils.ErrorCode;
import project.first.spring.Utilities.encryption.EncryptionService;
import project.first.spring.Utilities.exceptions.OnboardingException;
import project.first.spring.flows.Beer.Exceptions.NotFoundException;
import project.first.spring.flows.Onboarding.entities.Customer;
import project.first.spring.flows.Onboarding.mapper.CustomerMapper;
import project.first.spring.flows.Onboarding.model.CustomerDTO;
import project.first.spring.flows.Onboarding.model.LoginDto;
import project.first.spring.flows.Onboarding.model.SignUpDto;
import project.first.spring.flows.Onboarding.pageRender.constants.OnboardingConstants;
import project.first.spring.flows.Onboarding.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private EncryptionService encryptionService;

    @Override
    public List<CustomerDTO> customerList() {
        return customerRepository.findAll()
                .stream().map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID id) {
        return Optional.of(customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO))
                .orElse(null);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {
        // read about AtomicReference
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setUsername(customerDTO.getUsername());
            customerRepository.save(foundCustomer);
        },NotFoundException::new);
    }

    @Override
    public boolean deleteById(UUID customerId) {
        if(customerRepository.findById(customerId).isPresent()){
            customerRepository.deleteById(customerId);
            return true;
        }
        else
            return false;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(NotFoundException::new);
        if(StringUtils.hasText(customerDTO.getUsername()))
            customer.setUsername(customerDTO.getUsername());
        customerRepository.save(customer);
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<Customer> customer = customerRepository.findByEmail(loginDto.getEmail());
        if (customer.isEmpty())
            throw new OnboardingException(ErrorCode.ONBOARDING_01);

        if (!encryptionService.verifyPassword(loginDto.getPassword(), customer.get().getPassword()))
            throw new OnboardingException(ErrorCode.ONBOARDING_02);

        return OnboardingConstants.LOGIN_SUCCESS_MESSAGE;
    }

    @Override
    public String signUp(SignUpDto signUpDto) {
        Optional<Customer> customer = customerRepository.findByEmail(signUpDto.getEmail());
        if (customer.isPresent())
            throw new OnboardingException(ErrorCode.ONBOARDING_03);

        Customer customerToSignUp = Customer.builder()
                .username(signUpDto.getFirstName() + " " + signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .build();

        customerRepository.save(customerToSignUp);

        return OnboardingConstants.SIGN_UP_SUCCESS_MESSAGE;
    }
}
