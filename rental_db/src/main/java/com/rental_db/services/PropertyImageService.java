package com.rental_db.services;

import com.rental_db.dto.PropertyImageDTO;
import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyImage;
import com.rental_db.repositories.PropertyImageRepository;
import com.rental_db.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PropertyImageService {

    private final PropertyImageRepository repo;
    private PropertyRepository propertyRepo;

    public PropertyImageService(PropertyImageRepository repo) {
        this.repo = repo;
    }

    public PropertyImage addImage(PropertyImageDTO dto) {

        Property property = propertyRepo.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        PropertyImage img = new PropertyImage();
        img.setProperty(property);
        img.setImageUrl(dto.getImageUrl());
        img.setCreatedAt(LocalDateTime.now());

        return repo.save(img);
    }
}

