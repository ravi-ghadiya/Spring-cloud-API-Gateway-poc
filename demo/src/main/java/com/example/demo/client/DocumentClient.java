package com.example.demo.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "Document", url = "${feign.url}")
public interface DocumentClient {
    @PostMapping("/documents/protected/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam MultipartFile file, @RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId);

    @GetMapping("/documents/private/view")
    public Object findAllDocuments(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId);

    @GetMapping("/documents/protected/test")
    public String test(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId);
}
