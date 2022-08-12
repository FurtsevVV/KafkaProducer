package com.zakat.kafkaexample.somepapka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProducerService {

    public static final String TOPIC_NAME = "topic1";
    public static final String GROUP_ID = "group_id";
    public static final String KEY = "KEY1";
    public static final String KEY2 = "KEY2";


    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);


    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTemplate<String, Message> messageKafkaTemplate;



    public void send(String topic, String payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
       ListenableFuture<SendResult<String, String>> future1 = kafkaTemplate.send(topic, 1, KEY, payload);
       future1.addCallback(System.out::println, System.err::println);
       kafkaTemplate.flush();
    }


    public void sendObject(String topic, Message payload) {
        payload.setDate(LocalDateTime.now());
        LOGGER.info("sending object Message='{}' to topic='{}'", payload, topic);
        ListenableFuture<SendResult<String, Message>> future1 = messageKafkaTemplate.send(topic, 2, KEY2, payload);
        future1.addCallback(System.out::println, System.err::println);
    }



}
