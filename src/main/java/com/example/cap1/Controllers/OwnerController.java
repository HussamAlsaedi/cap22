package com.example.cap1.Controllers;


import com.example.cap1.ApiResponse.ApiResponse;
import com.example.cap1.Models.Owner;
import com.example.cap1.Services.OwnerService;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    // Endpoint Retrieve all owners
    @GetMapping("/get")
    public List<Owner> getAllOwners() {

        return ownerService.getAllOwners();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addOwner(@RequestBody @Valid  Owner owner, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }
        ownerService.addOwner(owner);
        return  ResponseEntity.status(200).body(new ApiResponse("Successfully added owner."));
    }



    @PutMapping("/update/{ownerId}")
    public ResponseEntity<ApiResponse> updateOwner(@PathVariable Long ownerId, @RequestBody @Valid Owner ownerUpdate, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }
         ownerService.updateOwner(ownerId, ownerUpdate);
        return ResponseEntity.status(200).body(new ApiResponse("successfully updated Owner with id: " + ownerId));

    }


    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<ApiResponse> deleteOwner(@PathVariable Long ownerId) {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.status(200).body(new ApiResponse("successfully deleted Owner with id: " + ownerId));
    }


}
