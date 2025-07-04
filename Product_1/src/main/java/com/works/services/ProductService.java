package com.works.services;

import com.works.entities.Product;
import com.works.entities.dto.ProductAddDto;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final JmsTemplate jmsTemplate;

    public Map<String, String> addProduct(ProductAddDto productAddDto) {
        jmsTemplate.convertAndSend("productDestination",productAddDto);
        Map<String, String> result = new HashMap<>();
        result.put("status", "OK");
        return result;
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }


}
