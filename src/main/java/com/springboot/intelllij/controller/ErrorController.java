package com.springboot.intelllij.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/error")
public class ErrorController {

    @GetMapping(value = "/unauthorized")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void unauthorized() {}
}
