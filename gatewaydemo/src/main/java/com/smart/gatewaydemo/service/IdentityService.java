package com.smart.gatewaydemo.service;

import com.smart.gatewaydemo.DTO.UserSession;
import com.smart.gatewaydemo.client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IdentityService {

    private final AuthenticationClient authenticationClient;
    private final RestTemplate restTemplate;
    private final String identityHostName;

    @Autowired
    public IdentityService(AuthenticationClient authenticationClient, @Value("${documentmanagement.host.identity}") String identityHostName) {
        this.authenticationClient = authenticationClient;
        this.identityHostName = identityHostName;
        this.restTemplate = new RestTemplate();
    }


    public Object authorize(String authToken) {
//        return authenticationClient.authorize(authToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authToken);
        HttpEntity entity = new HttpEntity(headers);
        System.out.println(identityHostName + "/private/authorize");
        return restTemplate.exchange(identityHostName + "/user/private/authorize", HttpMethod.GET, entity, UserSession.class).getBody();

    }
}
