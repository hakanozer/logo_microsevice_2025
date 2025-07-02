package com.works.services;

import com.works.entities.Customer;
import com.works.entities.dto.CustomerRegisterDto;
import com.works.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

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

}
