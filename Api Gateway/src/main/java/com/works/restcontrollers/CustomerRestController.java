package com.works.restcontrollers;

import com.works.entities.Customer;
import com.works.entities.dto.CustomerRegisterDto;
import com.works.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerRestController {

    final private CustomerService customerService;

    @PostMapping("register")
    public ResponseEntity<Object> register(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
       Customer customer = customerService.register(customerRegisterDto);
       if (customer == null) {
           return new ResponseEntity<>(customerRegisterDto.getUsername()+" all ready in use",HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
