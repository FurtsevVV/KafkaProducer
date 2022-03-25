package com.zakat.kafkaexample;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private ProducerService producerService;


    @PostMapping("/generate")
    public String generate(@RequestBody Message message) {
        producerService.produce(message);
        return "OK";
    }

}
