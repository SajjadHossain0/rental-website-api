package com.rental_db.services;

import com.rental_db.dto.PropertyDTO;
import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyType;
import com.rental_db.repositories.PropertyRepository;
import com.rental_db.repositories.PropertyTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository repo;
    private final PropertyTypeRepository typeRepo;

    public PropertyService(PropertyRepository repo, PropertyTypeRepository typeRepo) {
        this.repo = repo;
        this.typeRepo = typeRepo;
    }

    public Property create(PropertyDTO dto) {

        PropertyType type = typeRepo.findById(dto.getPropertyTypeId())
                .orElseThrow(() -> new RuntimeException("Property type not found"));

        Property p = new Property();
        p.setUniqueId("RENT-" + System.currentTimeMillis());
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setSecurityDeposit(dto.getSecurityDeposit());
        p.setPropertyType(type);

        p.setBedroom(dto.getBedroom());
        p.setBathroom(dto.getBathroom());
        p.setBalcony(dto.getBalcony());
        p.setArea(dto.getArea());
        p.setAmenities(dto.getAmenities());
        p.setAddress(dto.getAddress());
        p.setLocation(dto.getLocation());

        p.setAvailable(true);
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());

        return repo.save(p);
    }

    public List<Property> getAll() {
        return repo.findAll();
    }

    public Property update(Long id, PropertyDTO dto) {
        Property p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        p.setUpdatedAt(LocalDateTime.now());
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());

        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

