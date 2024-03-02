package com.example.sec.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@CrossOrigin("*")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello from secured controleer", HttpStatus.ACCEPTED);
    }
}
