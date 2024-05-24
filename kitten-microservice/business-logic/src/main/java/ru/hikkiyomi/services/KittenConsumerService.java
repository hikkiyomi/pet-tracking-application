package ru.hikkiyomi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.models.Kitten;

import java.util.List;
import java.util.Optional;

@Service
public class KittenConsumerService {
    @Autowired
    private KittenService kittenService;

    @KafkaListener(topics = "${kafka.topics.create}", groupId = "kitten_group",
            containerFactory = "kittenKafkaListenerContainerFactory")
    public void post(Kitten kitten) {
        kittenService.save(kitten);
    }

    @KafkaListener(topics = "${kafka.topics.update}", groupId = "kitten_group",
            containerFactory = "kittenKafkaListenerContainerFactory")
    public void put(Kitten kitten) {
        kittenService.save(kitten);
    }

    @KafkaListener(topics = "${kafka.topics.delete}", groupId = "kitten_group",
            containerFactory = "kittenKafkaListenerContainerFactory")
    public void delete(Kitten kitten) {
        kittenService.delete(kitten);
    }

    public Optional<Kitten> findById(Long id) {
        return kittenService.findById(id);
    }

    public List<Kitten> findAll() {
        return kittenService.findAll();
    }
}
