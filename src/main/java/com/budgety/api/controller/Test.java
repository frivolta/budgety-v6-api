package com.budgety.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class Test {

    @GetMapping
    public ResponseEntity<String> getPrivateTest(Authentication principal){
        return ResponseEntity.ok(principal.getName());
    }


    @PostMapping
    public ResponseEntity<String> getPublicTest(Authentication principal){
        return ResponseEntity.ok(principal.getName());
    }
}
