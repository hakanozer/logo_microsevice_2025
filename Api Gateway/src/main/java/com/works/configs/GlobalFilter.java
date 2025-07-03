package com.works.configs;

import com.works.entities.Info;
import com.works.repositories.InfoRepository;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GlobalFilter implements Filter {

    final private InfoRepository infoRepo;
    final private Tracer tracer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        String roles = "";
        if (auth != null) {
            username = auth.getName();
            roles = auth.getAuthorities().toString();
        }
        String url = request.getRequestURI();
        String sessionId = request.getRequestedSessionId();
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        long time = System.currentTimeMillis();

        Info info = new Info(null, username, roles, url, sessionId, userAgent, ip, time);
        infoRepo.save(info);

        CurrentTraceContext traceContext = tracer.currentTraceContext();
        if (traceContext.context() != null) {
            String spanId = traceContext.context().spanId();
            String traceId = traceContext.context().traceId();
            //System.out.println( spanId + " " + traceId + " " + parentId );
            response.setHeader("TraceId", traceId);
            response.setHeader("SpanId", spanId);
        }
        filterChain.doFilter(request, response);
    }

}
