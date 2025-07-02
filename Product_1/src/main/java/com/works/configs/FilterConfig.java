package com.works.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class FilterConfig implements Filter {

    @Value("${api.ip}")
    private String apiIP;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String fullUrl = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String agent = request.getHeader("User-Agent");
        String sessionId = request.getSession().getId();

        System.out.println(fullUrl + " " + uri + " " + ip + " " + agent + " " + sessionId);
        if (ip.equals(apiIP)) {
            filterChain.doFilter(request, response);
        }else {
            //response.sendError(HttpServletResponse.SC_FORBIDDEN);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            PrintWriter print = response.getWriter();
            print.write("{ \"status\": false, \"message\": \"Forbidden\" }");
        }
    }

}
