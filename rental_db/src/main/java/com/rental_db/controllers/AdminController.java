package com.rental_db.controllers;

import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyType;
import com.rental_db.services.PropertyService;
import com.rental_db.services.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PropertyService propertyService;
    private final PropertyTypeService propertyTypeService;

    @Autowired
    public AdminController(PropertyService propertyService, PropertyTypeService propertyTypeService) {
        this.propertyService = propertyService;
        this.propertyTypeService = propertyTypeService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        // Add summary data here if needed in the future
        return "admin/dashboard";
    }

    @GetMapping("/properties/add")
    public String addPropertyForm(Model model) {
        model.addAttribute("pageTitle", "Add Property");
        model.addAttribute("property", new Property());
        List<PropertyType> propertyTypes = propertyTypeService.getAll();
        model.addAttribute("propertyTypes", propertyTypes);
        return "admin/add-property";
    }

    @PostMapping("/properties/save")
    public String saveProperty(@ModelAttribute Property property) {
        propertyService.saveProperty(property);
        return "redirect:/admin";
    }

    @GetMapping("/property-types")
    public String listPropertyTypes(Model model) {
        model.addAttribute("pageTitle", "Property Types");
        model.addAttribute("propertyTypes", propertyTypeService.getAll());
        return "admin/property-types";
    }

    @GetMapping("/property-types/add")
    public String addPropertyTypeForm(Model model) {
        model.addAttribute("pageTitle", "Add Property Type");
        model.addAttribute("propertyType", new PropertyType());
        return "admin/add-property-type";
    }

    @PostMapping("/property-types/save")
    public String savePropertyType(@ModelAttribute PropertyType propertyType) {
        propertyTypeService.save(propertyType);
        return "redirect:/admin/property-types";
    }
}
