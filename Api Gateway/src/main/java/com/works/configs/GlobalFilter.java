package com.works.configs;

import com.works.entities.Info;
import com.works.repositories.InfoRepository;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class GlobalFilter implements WebFilter {

    private final InfoRepository infoRepo;
    private final Tracer tracer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        CurrentTraceContext traceContext = tracer.currentTraceContext();
        if (traceContext.context() != null) {
            String spanId = traceContext.context().spanId();
            String traceId = traceContext.context().traceId();
            headers.add("TraceId", traceId);
            headers.add("SpanId", spanId);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        String roles = "";

        if (auth != null) {
            username = auth.getName();
            roles = auth.getAuthorities().iterator().next().toString();
        }

        String url = exchange.getRequest().getURI().getPath();
        String sessionId = exchange.getRequest().getCookies().getFirst("JSESSIONID") != null ?
                exchange.getRequest().getCookies().getFirst("JSESSIONID").getValue() : null;
        String userAgent = exchange.getRequest().getHeaders().getFirst("User-Agent");
        String ip = exchange.getRequest().getRemoteAddress() != null ?
                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() : "unknown";
        long time = System.currentTimeMillis();
        Info i = new Info(null, username, roles, url, sessionId, userAgent, ip, time);
        infoRepo.save(i);
        System.out.println(i);
       return chain.filter(exchange);

    }


}
