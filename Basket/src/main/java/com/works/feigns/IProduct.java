package com.works.feigns;

import com.works.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("product")
public interface IProduct {

    @GetMapping("/product/list")
    List<Product> productList();

    @GetMapping("/product/single")
    Product productSingle(@RequestParam Long id);

}
