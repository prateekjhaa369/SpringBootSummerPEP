package com.example.icicibankserviceapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ICICIBankServiceController {

    @GetMapping("/bank")
    public String getBank() {
        return "ICICI Bank Service Running";
    }

}