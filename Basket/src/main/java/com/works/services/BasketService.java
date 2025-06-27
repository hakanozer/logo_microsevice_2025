package com.works.services;

import com.works.entities.Basket;
import com.works.feigns.IBulut;
import com.works.feigns.IProduct;
import com.works.models.BasketModel;
import com.works.models.JwtLogin;
import com.works.models.LoginModel;
import com.works.models.Product;
import com.works.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final DiscoveryClient discoveryClient;
    private final BasketRepository basketRepository;
    private final IProduct iProduct;
    private final IBulut iBulut;

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public List<BasketModel> basketList() {
        /*
        List<ServiceInstance> list = discoveryClient.getInstances("product");
        if (list != null && !list.isEmpty()) {
            ServiceInstance serviceInstance = list.get(0);
            String uri = serviceInstance.getUri().toString();
            uri = uri + "/product/list";
            RestTemplate restTemplate = new RestTemplate();
            //String response = restTemplate.getForObject(uri, String.class);
            ResponseEntity<Product[]> products = restTemplate.getForEntity(uri, Product[].class);
            Product[] productArray = products.getBody();
            System.out.println( productArray[0].getPid() );
        }
         */
        List<BasketModel> basketModels = new ArrayList<>();
        List<Basket> basketList = basketRepository.findAll();
        for (Basket basket : basketList) {
            Product pro = iProduct.productSingle(basket.getPid());
            BasketModel basketModel = new BasketModel();
            basketModel.setBasket(basket);
            basketModel.setProduct(pro);
            basketModels.add(basketModel);
        }
        return basketModels;
    }

    public JwtLogin login(LoginModel loginModel) {
        return iBulut.login(loginModel);
    }


}
