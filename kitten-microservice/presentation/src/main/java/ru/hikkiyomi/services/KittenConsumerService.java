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

    @KafkaListener(topics = "${kafka.topics.create.kitten}", groupId = "kitten_group",
            containerFactory = "kittenKafkaListenerContainerFactory")
    public void post(Kitten kitten) {
        kittenService.save(kitten);
        System.out.println("aaaa");
    }

    @KafkaListener(topics = "${kafka.topics.update.kitten}", groupId = "kitten_group",
            containerFactory = "kittenKafkaListenerContainerFactory")
    public void put(Kitten kitten) {
        kittenService.save(kitten);
        System.out.println("aaaa");
    }

    @KafkaListener(topics = "${kafka.topics.delete.kitten}", groupId = "kitten_group",
            containerFactory = "kittenKafkaListenerContainerFactory")
    public void delete(Kitten kitten) {
        kittenService.delete(kitten);
        System.out.println("aaaa");
    }

    public Optional<Kitten> findById(Long id) {
        return kittenService.findById(id);
    }

    public List<Kitten> findAll() {
        return kittenService.findAll();
    }
}
