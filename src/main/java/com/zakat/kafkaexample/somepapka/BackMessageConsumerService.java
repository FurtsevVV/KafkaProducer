package com.zakat.kafkaexample.somepapka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BackMessageConsumerService {

    public static final String TOPIC_NAME = "topic1";
    public static final String GROUP_ID = "group_id";
    private final String MESSAGE_GROUP_ID = "message_group_id";
    public static final String KEY = "KEY1";
    public static final String KEY2 = "KEY2";

    @KafkaListener(groupId = MESSAGE_GROUP_ID, containerFactory = "backMessageKafkaListenerContainerFactory",
            topicPartitions = {@TopicPartition(topic = TOPIC_NAME, partitions = {"0"})})
    public void consumeBackMessage(ConsumerRecord<String, BackMessage> message)
    {
        log.info("BackMessage: " + message);



    }
}
