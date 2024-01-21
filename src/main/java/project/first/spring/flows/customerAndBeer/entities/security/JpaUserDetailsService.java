package project.first.spring.flows.customerAndBeer.entities.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.first.spring.flows.customerAndBeer.entities.Customer;
import project.first.spring.flows.customerAndBeer.repositories.CustomerRepository;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Getting User info via JPA");

        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User name: " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(),
                customer.getEnabled(), customer.getAccountNonExpired(), customer.getCredentialsNonExpired(),
                customer.getAccountNonLocked(), convertToSpringAuthorities(customer.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<project.first.spring.flows.customerAndBeer.entities.security.Authority> authorities) {
        if (authorities != null && authorities.size() > 0){
            return authorities.stream()
                    .map(project.first.spring.flows.customerAndBeer.entities.security.Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}
