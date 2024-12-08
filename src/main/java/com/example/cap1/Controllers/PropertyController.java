package com.example.cap1.Controllers;

import com.example.cap1.ApiResponse.ApiResponse;
import com.example.cap1.Models.Property;
import com.example.cap1.Services.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;


    @GetMapping("/get")
    public  List<Property> getAllProperties() {

        return propertyService.getAllProperties();
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProperty(@RequestBody @Valid Property property, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage) );
        }
        propertyService.addProperty(property);
        return ResponseEntity.status(201).body(new ApiResponse("successfully added property"));
    }


    @PutMapping("/update/{propertyId}")
    public ResponseEntity<ApiResponse> updateProperty(@PathVariable Long propertyId, @RequestBody @Valid Property updatedProperty, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage) );
        }
        propertyService.updateProperty(propertyId, updatedProperty);
         return ResponseEntity.status(200).body(new ApiResponse("successfully updated property with id: " + propertyId));

    }


    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<ApiResponse> deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
        return ResponseEntity.status(200).body(new ApiResponse("successfully deleted property with id: " + propertyId));

    }
     // Endpoint # 6
     // find all  Property are available
    @GetMapping("/findProperty")
    public List<Property> findProperties(){
        return propertyService.findProperties();
    }

    //  Endpoint # 7
    //  Find Properties by Type
    @GetMapping("/type/{propertyType}")
    public List<Property> getPropertiesByType(@PathVariable String propertyType) {
        return propertyService.getPropertiesByType(propertyType);
    }

    //  Endpoint # 8
    // Find Properties by Price Range and Type

    @GetMapping("/price-rangeAndType/{minPrice}/{maxPrice}/{propertyType}")
    public List<Property> getPropertiesByPriceRange(@PathVariable Double minPrice, @PathVariable Double maxPrice,@PathVariable String propertyType) {
        return propertyService.getPropertiesByPriceRangeAndType(minPrice, maxPrice,propertyType);
    }


}
