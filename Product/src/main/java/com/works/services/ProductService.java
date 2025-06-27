package com.works.services;

import com.works.entities.Product;
import com.works.entities.dto.ProductAddDto;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product findProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }

}
