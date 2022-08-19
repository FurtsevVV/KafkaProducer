package com.zakat.kafkaexample.somepapka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaProducerConfig
{
    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    public final String GROUP_ID = "group_id";
    private final String MESSAGE_GROUP_ID = "message_group_id";

    private final String topicname = "topic1";


    private Map<String, Object> getStringProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    private Map<String, Object> getMessageProperties() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    // linger.ms - время искусственной задержки перед отправкой сообщений в топики чтобы объединить записи в пакет
    // batch.size - размер пакета в который объединяются сообщения
    private Map<String, Object> getListStringProperties() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    //передаем имя топика, количество партиций, 1 - коэффициент репликации
    //определяет, сколько экземпляров брокера в кластере должны содержать журналы для этой партиции
    @Bean
    public NewTopic adviceTopic() {
        return new NewTopic(topicname, 4, (short) 1);
    }

    //конфиги для передачи String
    //1. Send string to Kafka. Значения по ключу KEY_SERIALIZER_CLASS_CONFIG хранятся в одной партиции

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        return new DefaultKafkaProducerFactory<>(getStringProperties());
    }


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    //конфиги для передачи списка строк
    @Bean
    public ProducerFactory<String, List<String>> producerListFactory() {

        return new DefaultKafkaProducerFactory<>(getListStringProperties());
    }


    @Bean
    public KafkaTemplate<String, List<String>> listKafkaTemplate() {
        return new KafkaTemplate<>(producerListFactory());
    }


    //2. Конфиги для передачи объекта Send Message objects to Kafka.

    @Bean
    public KafkaTemplate<String, Message> userKafkaTemplate() {
        return new KafkaTemplate<>(messageProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Message> messageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getMessageProperties());
    }
}


