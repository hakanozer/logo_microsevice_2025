package com.works.configs;

import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
         http
                .httpBasic(Customizer.withDefaults())
                .authorizeExchange(
                req -> req
                        .pathMatchers("/product/**").hasRole("product")
                        .pathMatchers("/basket/**").hasRole("basket")
                        .pathMatchers("/customer/**").permitAll()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }

}

/*
ali -> product
veli -> basket
zehra -> product, basket
 */
