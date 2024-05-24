package ru.hikkiyomi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.models.Kitten;

@Service
public class KittenProducerService {
    @Value("${kafka.topics.create}")
    private String topicPost;

    @Value("${kafka.topics.update}")
    private String topicPut;

    @Value("${kafka.topics.delete}")
    private String topicDelete;

    @Autowired
    private KafkaTemplate<String, Kitten> kafkaTemplate;

    public void post(Kitten kitten) {
        kafkaTemplate.send(topicPost, kitten);
    }

    public void put(Kitten kitten) {
        kafkaTemplate.send(topicPut, kitten);
    }

    public void delete(Kitten kitten) {
        kafkaTemplate.send(topicDelete, kitten);
    }
}
