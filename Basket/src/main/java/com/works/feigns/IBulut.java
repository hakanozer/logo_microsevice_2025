package com.works.feigns;

import com.works.models.JwtLogin;
import com.works.models.LoginModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "jsonBulut", url = "https://jsonbulut.com/api/")
public interface IBulut {

    @PostMapping("auth/login")
    JwtLogin login(@RequestBody LoginModel loginModel);

}
