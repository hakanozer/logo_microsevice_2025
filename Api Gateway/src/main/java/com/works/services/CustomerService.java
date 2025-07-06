package com.works.services;

import com.works.entities.Customer;
import com.works.entities.Role;
import com.works.entities.dto.CustomerRegisterDto;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService implements ReactiveUserDetailsService {

    final private ModelMapper modelMapper;
    final private PasswordEncoder passwordEncoder;
    final private CustomerRepository customerRepository;


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

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsernameEqualsIgnoreCase(username);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return Mono.just(
                    User.withUsername(customer.getUsername())
                            .password(customer.getPassword())
                            .roles(parseRoles(customer.getRoles()))
                            .build()
            );
        }
        return Mono.empty();
    }

    private String[] parseRoles(List<Role> roles) {
        return roles.stream().map(Role::getName).toArray(String[]::new);
    }


}
