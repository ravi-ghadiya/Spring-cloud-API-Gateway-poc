package com.smart.gatewaydemo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "Auth", url = "${documentmanagement.host.identity}")
public interface AuthenticationClient {
    @GetMapping(path = "/user/private/authorize", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object authorize(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId);

}
