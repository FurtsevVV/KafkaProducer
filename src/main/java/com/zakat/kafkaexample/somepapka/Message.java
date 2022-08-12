package com.zakat.kafkaexample.somepapka;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private String author;
    private Long number;
    private LocalDateTime date;


}
