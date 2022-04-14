package com.smart.gatewaydemo.auth;

import com.smart.gatewaydemo.DTO.UserSession;
import com.smart.gatewaydemo.service.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class GatewayAuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayAuthenticationManager.class);
    private final IdentityService identityService;

    public GatewayAuthenticationManager(IdentityService identityService) {
        this.identityService = identityService;
    }


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            UserSession session = (UserSession) identityService.authorize(authToken);

            if (Objects.isNull(session)) {
                LOGGER.error("Unauthorized access " + authentication.getName());
                return Mono.empty();
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(session.getSessionId(), session.getSessionId(), List.of(new SimpleGrantedAuthority("ADMIN")));
            return Mono.just(auth);


        } catch (Exception e) {
            LOGGER.error("Unauthorized access " + authentication.getName(), e);
            return Mono.empty();
        }
    }
}
