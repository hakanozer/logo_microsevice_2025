package com.works.restcontrollers;

import com.works.entities.Product;
import com.works.entities.dto.ProductAddDto;
import com.works.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("add")
    public Map<String, String> add(@Valid @RequestBody ProductAddDto productAddDto) {
        return productService.addProduct(productAddDto);
    }

    @GetMapping("list")
    public List<Product> list() {
        return productService.findAllProduct();
    }

    @GetMapping("single")
    public Product findById(@RequestParam Long id) {
        return productService.findProductById(id);
    }


}
