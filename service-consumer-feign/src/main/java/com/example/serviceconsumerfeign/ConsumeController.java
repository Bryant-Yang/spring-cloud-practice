package com.example.serviceconsumerfeign;

import com.example.serviceconsumerfeign.feign.ComputeFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumeController {

    @Autowired
    private ComputeFeignInterface computeFeignInterface;

    @GetMapping(value = "/add")
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return computeFeignInterface.add(a, b);
    }
}
