package com.zakat.kafkaexample.somepapka;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class BackMessage {

    private String name;
    private Long qty;
    private Message messsage;
}
