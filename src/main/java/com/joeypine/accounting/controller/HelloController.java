package com.joeypine.accounting.controller;

import com.joeypine.accounting.model.service.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private AtomicLong countor = new AtomicLong();

    @GetMapping("v1/greeting")
    public Greeting sayHello(@RequestParam("name") String name) {
        return new Greeting(countor.incrementAndGet(), String.format("Hello,%s", name));
    }
}
