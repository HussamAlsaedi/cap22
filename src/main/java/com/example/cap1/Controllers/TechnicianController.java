package com.example.cap1.Controllers;

import com.example.cap1.ApiResponse.ApiResponse;
import com.example.cap1.Models.Technician;
import com.example.cap1.Services.TechnicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technicians")
@RequiredArgsConstructor
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping("/get")
    public List<Technician> getAllTechnicians() {
        return technicianService.findAllTechnicians();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTechnician(@RequestBody  @Valid Technician technician, Errors errors) {

        if (errors.hasErrors()) {
                String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }
        technicianService.addTechnician(technician);
        return ResponseEntity.status(200).body(new ApiResponse("successfully added Technician."));
    }

@PutMapping("/update/{technicianId}")
    public ResponseEntity<ApiResponse> updateTechnician(@PathVariable Long technicianId, @RequestBody  @Valid Technician technician, Errors errors) {

        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }
        technicianService.updateTechnician(technicianId, technician);
        return ResponseEntity.status(200).body(new ApiResponse("successfully updated Technician with id " + technicianId));
    }

    @DeleteMapping("/delete/{technicianId}")
    public ResponseEntity<ApiResponse> deleteTechnician(@PathVariable Long technicianId) {
        technicianService.deleteTechnician(technicianId);
        return ResponseEntity.status(200).body(new ApiResponse("successfully deleted technician with id: " + technicianId));
    }



}
