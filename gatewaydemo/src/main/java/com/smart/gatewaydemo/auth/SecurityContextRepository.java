package com.smart.gatewaydemo.auth;

import com.smart.gatewaydemo.config.PathSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final Logger log = LoggerFactory.getLogger(SecurityContextRepository.class);
    private final ReactiveAuthenticationManager authenticationManager;
    private final PathSettings pathSettings;

    public SecurityContextRepository(ReactiveAuthenticationManager authenticationManager, PathSettings pathSettings) {
        this.authenticationManager = authenticationManager;
        this.pathSettings = pathSettings;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("operation not supported.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("Secured Path: {}", request.getPath());
        String authHeader = null;

        if (!Objects.isNull(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))) {
            authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        } else if (!Objects.isNull(request.getQueryParams().getFirst(HttpHeaders.AUTHORIZATION))) {
            authHeader = request.getQueryParams().getFirst(HttpHeaders.AUTHORIZATION);
        }
        if (isPublicPath(exchange.getRequest().getURI().getPath())){
            return Mono.empty();
        }
        if (!Objects.isNull(authHeader)){
            Authentication auth = new UsernamePasswordAuthenticationToken(authHeader, authHeader);
            return authenticationManager.authenticate(auth).map(authentication -> new SecurityContextImpl(authentication));
        }
        else {
            return Mono.empty();
        }

    }

    private boolean isPublicPath(String requestpath) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> publicPaths = Arrays.stream(pathSettings.getPublicPath()).collect(Collectors.toList());
        for (String path : publicPaths) {
            if (pathMatcher.match(path, requestpath)){
                return true;
            }
        }
        return false;
    }
}
