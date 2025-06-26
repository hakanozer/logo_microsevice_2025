package com.works.services;

import com.works.entities.Product;
import com.works.entities.dto.ProductAddDto;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Product addProduct(ProductAddDto productAddDto) {
        Product product = modelMapper.map(productAddDto, Product.class);
        productRepository.save(product);
        return product;
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

}
