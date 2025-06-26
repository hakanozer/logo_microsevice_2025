package com.works.services;

import com.works.entities.Basket;
import com.works.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final DiscoveryClient discoveryClient;
    private final BasketRepository basketRepository;

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public List<Basket> basketList() {
        List<ServiceInstance> list = discoveryClient.getInstances("product");
        if (list != null && !list.isEmpty()) {
            ServiceInstance serviceInstance = list.get(0);
            String uri = serviceInstance.getUri().toString();
            uri = uri + "/product/list";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class);
            System.out.println( response );
        }
        return basketRepository.findAll();
    }

}
