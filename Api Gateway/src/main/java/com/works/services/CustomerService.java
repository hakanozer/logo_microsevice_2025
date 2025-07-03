package com.works.services;

import com.works.entities.Customer;
import com.works.entities.Role;
import com.works.entities.dto.CustomerRegisterDto;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService implements UserDetailsService {

    final private ModelMapper modelMapper;
    final private PasswordEncoder passwordEncoder;
    final private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameEqualsIgnoreCase(username);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return new User(
                    customer.getUsername(),
                    customer.getPassword(),
                    customer.getEnabled(),
                    true,
                    true,
                    true,
                    parseRoles(customer.getRoles())
            );
        }
        throw new UsernameNotFoundException("Username or Password Error");
    }

    private Collection<? extends GrantedAuthority> parseRoles(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            GrantedAuthority  grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

    public Customer register(CustomerRegisterDto customerRegisterDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameEqualsIgnoreCase(customerRegisterDto.getUsername());
        if (optionalCustomer.isPresent()) {
            return null;
        }else {
            Customer customer = modelMapper.map(customerRegisterDto, Customer.class);
            customer.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));
            return customerRepository.save(customer);
        }
    }

}
