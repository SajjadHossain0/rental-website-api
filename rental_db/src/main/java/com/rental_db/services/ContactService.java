package com.rental_db.services;

import com.rental_db.dto.ContactDTO;
import com.rental_db.entities.Contact;
import com.rental_db.entities.PropertyType;
import com.rental_db.repositories.ContactRepository;
import com.rental_db.repositories.PropertyTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactService {

    private final ContactRepository repo;
    private final PropertyTypeRepository typeRepo;

    public ContactService(ContactRepository repo, PropertyTypeRepository typeRepo) {
        this.repo = repo;
        this.typeRepo = typeRepo;
    }

    public Contact create(ContactDTO dto) {

        PropertyType type = typeRepo.findById(dto.getPropertyTypeId())
                .orElseThrow(() -> new RuntimeException("Property type not found"));

        Contact c = new Contact();
        c.setUniqueId("CNT-" + System.currentTimeMillis());
        c.setFullName(dto.getFullName());
        c.setEmail(dto.getEmail());
        c.setContactNumber(dto.getContactNumber());
        c.setDescription(dto.getDescription());
        c.setPropertyType(type);

        c.setBedroom(dto.getBedroom());
        c.setBathroom(dto.getBathroom());
        c.setBalcony(dto.getBalcony());
        c.setArea(dto.getArea());
        c.setPrice(dto.getPrice());
        c.setAddress(dto.getAddress());
        c.setLocation(dto.getLocation());
        c.setImageTempUrls(dto.getImageTempUrls());

        c.setStatus("pending");
        c.setCreatedAt(LocalDateTime.now());

        return repo.save(c);
    }
}

