package com.example.springsecurityjwtfinalpractise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/res")
public class DemoController {
    @GetMapping("/hello")
    public String hello(){

        return"helllo";
}

}
