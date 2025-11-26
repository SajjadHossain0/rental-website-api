package com.rental_db.controllers;

import com.rental_db.dto.PropertyDTO;
import com.rental_db.entities.Property;
import com.rental_db.services.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    @PostMapping
    public Property create(@RequestBody PropertyDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Property> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Property update(@PathVariable Long id, @RequestBody PropertyDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}

