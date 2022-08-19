package com.zakat.kafkaexample.somepapka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaBackMessageConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String kafkaServer;

    private final String GROUP_ID = "group_id";
    private final String MESSAGE_GROUP_ID = "message_group_id";
    private final String topicname = "topic1";


    TopicPartition partition1 = new TopicPartition(topicname, 1);
    TopicPartition partition2 = new TopicPartition(topicname, 2);
    TopicPartition partition0 = new TopicPartition(topicname, 0);



      //конфигурация потребителя для объекта BackMessage
    public ConsumerFactory<String, BackMessage> messageConsumerFactory() {


        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, MESSAGE_GROUP_ID);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(BackMessage.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BackMessage>
    backMessageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BackMessage> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(messageConsumerFactory());
        return factory;
    }




    @Bean
    public StringJsonMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}




