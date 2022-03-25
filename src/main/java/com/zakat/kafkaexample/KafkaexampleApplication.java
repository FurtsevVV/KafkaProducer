package com.zakat.kafkaexample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaexampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaexampleApplication.class, args);
    }




}
