package com.colegio.controller; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class StatusController{
    @GetMapping("/status")
public String status() {
    return "OK";
}
}
