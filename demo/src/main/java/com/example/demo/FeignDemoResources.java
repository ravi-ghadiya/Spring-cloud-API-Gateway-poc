package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FeignDemoResources {

    private FeignDemoService feignDemoService;

    @Autowired
    public FeignDemoResources(FeignDemoService feignDemoService) {
        this.feignDemoService = feignDemoService;
    }

    @PostMapping("/feigndemo/protected/store")
    ResponseEntity<String> storeDocument(@RequestParam MultipartFile file, @RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId){
        return feignDemoService.store(file, sessionId);
    }

    @GetMapping("/feigndemo/protected/show")
    public Object findAllDocuments(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId){
        return feignDemoService.findAll(sessionId);
    }

    @GetMapping("/feigndemo/protected/test")
    public String testFeign(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionId){

        String testfeign = feignDemoService.testfeign(sessionId);
        return testfeign;
    }

    @GetMapping("/feigndemo/public/test")
    public String testpublic(){
        return "test of public API without Auth header";
    }


}
