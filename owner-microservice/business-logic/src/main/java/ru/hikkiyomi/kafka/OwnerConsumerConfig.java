package ru.hikkiyomi.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.hikkiyomi.models.Owner;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OwnerConsumerConfig {
    @Value("${kafka.url}")
    private String address;

    @Bean
    public ConsumerFactory<String, Owner> ownerConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Owner> ownerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Owner> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(ownerConsumerFactory());

        return factory;
    }
}
