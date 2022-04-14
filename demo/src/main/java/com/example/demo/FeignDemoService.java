package com.example.demo;

import com.example.demo.client.DocumentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FeignDemoService {

    private final DocumentClient documentClient;

    @Autowired
    public FeignDemoService(DocumentClient documentClient) {
        this.documentClient = documentClient;
    }

    public ResponseEntity<String> store(MultipartFile file, String sessionId){
        String fileName = file.getOriginalFilename();
        System.out.println("Uploading file ---------->" + fileName);
        return documentClient.uploadDocument(file, sessionId);
    }

    public Object findAll(String sessionId){
        System.out.println("finding all documents for particular user...........");
        return documentClient.findAllDocuments(sessionId);
    }

    public String testfeign(String sessionId) {
        return documentClient.test(sessionId);
    }
}
