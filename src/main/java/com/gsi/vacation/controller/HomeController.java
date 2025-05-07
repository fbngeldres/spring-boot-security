package com.gsi.vacation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hola() {
        return "default";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hola";
    }
}
