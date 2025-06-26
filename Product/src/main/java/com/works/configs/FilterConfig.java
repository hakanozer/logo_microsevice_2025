package com.works.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FilterConfig implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");
        String sessionId = request.getSession().getId();

        System.out.println(uri + " " + ip + " " + agent + " " + sessionId);
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            filterChain.doFilter(request, response);
        }
    }

}
