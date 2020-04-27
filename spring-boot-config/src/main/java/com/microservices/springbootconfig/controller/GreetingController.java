package com.microservices.springbootconfig.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
public class GreetingController {

    @Value("${my.greeting}")
    private String greeting;

    @Value("${db.connection}")
    private String connection;

    @Value("${my.list.values}")
    private List<String> listValues;

    private final Environment env;

    public GreetingController(Environment environment) {
        this.env = environment;
    }

    @GetMapping("/greeting")
    public String getGreeting() {
        return greeting + " " + connection + " " + listValues;
    }

    @GetMapping("/envdetails")
    public String getEnvDetails() {
        return env.toString();
    }

}
