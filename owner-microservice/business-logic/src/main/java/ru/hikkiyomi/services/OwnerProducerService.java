package ru.hikkiyomi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.models.Owner;

@Service
public class OwnerProducerService {
    @Value("${kafka.topics.create}")
    private String topicPost;

    @Value("${kafka.topics.update}")
    private String topicPut;

    @Value("${kafka.topics.delete}")
    private String topicDelete;

    @Autowired
    private KafkaTemplate<String, Owner> kafkaTemplate;

    public void post(Owner owner) {
        kafkaTemplate.send(topicPost, owner);
    }

    public void put(Owner owner) {
        kafkaTemplate.send(topicPut, owner);
    }

    public void delete(Owner owner) {
        kafkaTemplate.send(topicDelete, owner);
    }
}
