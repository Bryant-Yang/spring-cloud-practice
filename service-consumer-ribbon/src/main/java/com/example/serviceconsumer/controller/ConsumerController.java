package com.example.serviceconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/add")
    public String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return restTemplate.getForEntity(String.format("http://COMPUTE-SERVICE/add?a=%d&b=%d", a, b), String.class).getBody();
    }
}
