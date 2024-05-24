package ru.hikkiyomi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hikkiyomi.models.Owner;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerConsumerService {
    @Autowired
    private OwnerService ownerService;

    @KafkaListener(topics = "owner.event.post", groupId = "owner_group",
            containerFactory = "ownerKafkaListenerContainerFactory")
    public void post(Owner owner) {
        ownerService.save(owner);
    }

    @KafkaListener(topics = "owner.event.put", groupId = "owner_group",
            containerFactory = "ownerKafkaListenerContainerFactory")
    public void put(Owner owner) {
        ownerService.save(owner);
    }

    @KafkaListener(topics = "owner.event.delete", groupId = "owner_group",
            containerFactory = "ownerKafkaListenerContainerFactory")
    public void delete(Owner owner) {
        ownerService.delete(owner);
    }

    public Optional<Owner> findById(Long id) {
        return ownerService.findById(id);
    }

    public List<Owner> findAll() {
        return ownerService.findAll();
    }

    public Optional<Owner> findByName(String name) {
        return ownerService.findByName(name);
    }
}
