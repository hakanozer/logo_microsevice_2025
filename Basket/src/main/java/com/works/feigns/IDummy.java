package com.works.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dummyJson", url = "https://dummyjson.com/")
public interface IDummy {

    @GetMapping("products/{id}")
    String products(@PathVariable String id);

    @GetMapping("carts/{id}")
    String singleCard(@PathVariable String id);

}
