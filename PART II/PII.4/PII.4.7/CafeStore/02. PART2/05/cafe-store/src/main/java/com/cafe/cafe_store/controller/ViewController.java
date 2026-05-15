package com.cafe.cafe_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
        
    @GetMapping("/hello-page")
    public String helloPage() {
        System.out.println("helloPage() method called");
        return "hello";
    }
}
