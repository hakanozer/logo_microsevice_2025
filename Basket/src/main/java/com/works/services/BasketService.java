package com.works.services;

import com.works.entities.Basket;
import com.works.feigns.IBulut;
import com.works.feigns.IDummy;
import com.works.feigns.IProduct;
import com.works.models.BasketModel;
import com.works.models.JwtLogin;
import com.works.models.LoginModel;
import com.works.models.Product;
import com.works.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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

    @Value("${app.customData}")
    private String customData;

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final CircuitBreakerFactory circuitBreakerGlobal;

    private final DiscoveryClient discoveryClient;
    private final BasketRepository basketRepository;
    private final IProduct iProduct;
    private final IBulut iBulut;
    private final IDummy iDummy;

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
        System.out.println("pull data: " + customData);
        String data = circuitBreakerFactory.create("dummy").run(
                () -> iDummy.products("1000"),
                throwable -> fallBack("1000")
        );
        System.out.println(data);

        CircuitBreaker breaker = circuitBreakerGlobal.create("dummy1");
        String pullData = breaker.run(
                () -> iDummy.singleCard("1"),
                throwable -> fallBack("1")
        );
        System.out.println("dummy1:" + pullData);
        return basketModels;
    }

    private String call(String id) {
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        return "Call :" + id;
    }

    private String fallBack(String id) {
        return "Daha sonra deneyiniz!";
    }

    public JwtLogin login(LoginModel loginModel) {
        //int i = 1;
        //int sum = i / 0;
        return iBulut.login(loginModel);
    }


}
