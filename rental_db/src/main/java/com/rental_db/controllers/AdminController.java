package com.rental_db.controllers;

import com.rental_db.dto.PropertyDTO;
import com.rental_db.dto.PropertyImageDTO;
import com.rental_db.dto.PropertyTypeDTO;
import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyImage;
import com.rental_db.entities.PropertyType;
import com.rental_db.services.PropertyImageService;
import com.rental_db.services.PropertyService;
import com.rental_db.services.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PropertyService propertyService;
    private final PropertyTypeService propertyTypeService;
    private final PropertyImageService propertyImageService;

    @Autowired
    public AdminController(PropertyService propertyService, PropertyTypeService propertyTypeService, PropertyImageService propertyImageService) {
        this.propertyService = propertyService;
        this.propertyTypeService = propertyTypeService;
        this.propertyImageService = propertyImageService;
    }

    // --- Dashboard ---
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        
        List<Property> properties = propertyService.getAll();
        List<PropertyType> types = propertyTypeService.getAll();
        
        model.addAttribute("totalProperties", properties.size());
        model.addAttribute("activeListings", properties.stream().filter(p -> Boolean.TRUE.equals(p.getAvailable())).count());
        model.addAttribute("totalTypes", types.size());
        // Placeholder for users/revenue as we don't have services for them yet
        model.addAttribute("totalUsers", 0); 
        model.addAttribute("revenue", 0);

        return "admin/dashboard";
    }

    // --- Property Types ---
    @GetMapping("/property-types")
    public String listPropertyTypes(Model model) {
        model.addAttribute("pageTitle", "Property Types");
        model.addAttribute("types", propertyTypeService.getAll());
        return "admin/property-types";
    }

    @GetMapping("/property-types/add")
    public String addPropertyTypeForm(Model model) {
        model.addAttribute("pageTitle", "Add Property Type");
        model.addAttribute("type", new PropertyType());
        return "admin/add-property-type";
    }

    @GetMapping("/property-types/edit/{id}")
    public String editPropertyTypeForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Property Type");
        // Assuming we can find it, otherwise might need error handling
        PropertyType type = propertyTypeService.getAll().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("type", type);
        return "admin/add-property-type";
    }

    @PostMapping("/property-types/save")
    public String savePropertyType(@ModelAttribute PropertyType type) {
        propertyTypeService.save(type);
        return "redirect:/admin/property-types";
    }

    @GetMapping("/property-types/delete/{id}")
    public String deletePropertyType(@PathVariable Long id) {
        propertyTypeService.delete(id);
        return "redirect:/admin/property-types";
    }

    // --- Properties ---
    @GetMapping("/properties")
    public String listProperties(Model model) {
        model.addAttribute("pageTitle", "Properties");
        model.addAttribute("properties", propertyService.getAll());
        return "admin/properties";
    }

    @GetMapping("/properties/add")
    public String addPropertyForm(Model model) {
        model.addAttribute("pageTitle", "Add Property");
        model.addAttribute("property", new Property());
        model.addAttribute("propertyTypes", propertyTypeService.getAll());
        return "admin/add-property";
    }

    @GetMapping("/properties/edit/{id}")
    public String editPropertyForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Property");
        Property property = propertyService.getAll().stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("property", property);
        model.addAttribute("propertyTypes", propertyTypeService.getAll());
        
        if (property != null) {
            model.addAttribute("images", propertyImageService.findByPropertyId(id));
        }
        
        return "admin/add-property";
    }

    @PostMapping("/properties/save")
    public String saveProperty(@ModelAttribute Property property) {
        propertyService.saveProperty(property);
        return "redirect:/admin/properties";
    }

    @GetMapping("/properties/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.delete(id);
        return "redirect:/admin/properties";
    }

    // --- Property Images ---
    @PostMapping("/properties/{id}/images/add")
    public String addPropertyImage(@PathVariable Long id, @RequestParam("imageUrl") String imageUrl) {
        PropertyImageDTO dto = new PropertyImageDTO(id, imageUrl);
        propertyImageService.addImage(dto);
        return "redirect:/admin/properties/edit/" + id;
    }

    @GetMapping("/properties/images/delete/{id}")
    public String deletePropertyImage(@PathVariable Long id, @RequestParam("propertyId") Long propertyId) {
        propertyImageService.delete(id);
        return "redirect:/admin/properties/edit/" + propertyId;
    }
}
