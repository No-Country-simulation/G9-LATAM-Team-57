package com.energiai.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    @GetMapping
    public Map<String, String> healt(){
        return Map.of("status","UP");
    }
}
