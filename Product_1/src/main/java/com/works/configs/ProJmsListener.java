package com.works.configs;

import com.works.entities.Product;
import com.works.entities.dto.ProductAddDto;
import com.works.repositories.ProductRepository;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class ProJmsListener {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @JmsListener(destination = "productDestination", containerFactory = "productContainerFactory")
    public void addJmsProduct(ProductAddDto productAddDto, Message message) {
        try {
            System.out.println(message.getJMSMessageID());
            Thread.sleep(1000);
        }catch (Exception ex){}
        Product product = modelMapper.map(productAddDto, Product.class);
        product.setTitle(UUID.randomUUID().toString());
        productRepository.save(product);
    }

}