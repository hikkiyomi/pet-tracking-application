package ru.hikkiyomi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hikkiyomi.converters.OwnerDtoToOwnerConverter;
import ru.hikkiyomi.dtos.OwnerDto;
import ru.hikkiyomi.model.Owner;
import ru.hikkiyomi.service.OwnerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController implements BasicController<OwnerDto> {
    @Autowired
    private OwnerService service;

    private OwnerDtoToOwnerConverter converter = new OwnerDtoToOwnerConverter();

    @Override
    @PostMapping("/create")
    public ResponseEntity create(OwnerDto obj) {
        service.save(converter.convert(obj));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @GetMapping("/get/{id}")
    public OwnerDto getById(@PathVariable int id) {
        return new OwnerDto(service.findById(id));
    }

    @Override
    @GetMapping("/get")
    public List<OwnerDto> getAll() {
        List<Owner> owners = service.findAll();
        List<OwnerDto> dtos = new ArrayList<>();

        owners.forEach(owner -> dtos.add(new OwnerDto(owner)));

        return dtos;
    }

    @Override
    @PutMapping("/update/{id}")
    public void update(@PathVariable int id, @RequestBody OwnerDto obj) {
        Owner updating = service.findById(id);

        if (updating != null) {
            updating.setName(obj.getName());
            updating.setBirthDate(obj.getBirthDate());

            service.save(updating);
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        service.delete(service.findById(id));
    }
}
