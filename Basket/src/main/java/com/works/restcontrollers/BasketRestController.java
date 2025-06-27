package com.works.restcontrollers;

import com.works.entities.Basket;
import com.works.models.BasketModel;
import com.works.models.JwtLogin;
import com.works.models.LoginModel;
import com.works.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("basket")
public class BasketRestController {

    private final BasketService basketService;

    @PostMapping("add")
    public Basket add(@RequestBody Basket basket) {
        return basketService.save(basket);
    }

    @GetMapping("list")
    public List<BasketModel> list() {
        return basketService.basketList();
    }

    @PostMapping("login")
    public JwtLogin login(@RequestBody LoginModel loginModel) {
        return basketService.login(loginModel);
    }



}
