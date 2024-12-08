package com.example.cap1.Controllers;

import com.example.cap1.ApiResponse.ApiResponse;
import com.example.cap1.Models.ServiceRequest;
import com.example.cap1.Services.ServiceRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @GetMapping("/get")
    public List<ServiceRequest> getRequests() {

        return serviceRequestService.findAllServiceRequests();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addServiceRequest(@RequestBody @Valid ServiceRequest serviceRequest, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return  ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }

        serviceRequestService.addServiceRequest(serviceRequest);
        return  ResponseEntity.status(200).body(new ApiResponse("Successfully added service request"));
    }

    @PutMapping("/update/{RequestId}")
    public ResponseEntity<ApiResponse> updateServiceRequest(@PathVariable Long RequestId,  @RequestBody @Valid ServiceRequest serviceRequest, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return  ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }

        serviceRequestService.updateServiceRequest(RequestId, serviceRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated service request with id " + RequestId));
    }

    @DeleteMapping("/delete/{RequestId}")
    public ResponseEntity<ApiResponse> deleteServiceRequest(@PathVariable Long RequestId) {
        serviceRequestService.deleteServiceRequest(RequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted service request with id " + RequestId ));
    }

    // Endpoint  # 10
    // this method find all service request depends on role of technician role
    @GetMapping("/find-ServiceRequestsByRole/{technicianId}")
    public List<ServiceRequest> getAllServiceRequestsByRole(@PathVariable Long technicianId) {

        return  serviceRequestService.getAllServiceRequestsByRole(technicianId);

    }

    // Endpoint  # 11
    // assign Technician to service request
    @GetMapping("/assignTechnician/{requestId}/{technicianId}")
    public ResponseEntity<ApiResponse> assignTechnician(@PathVariable Long requestId,@PathVariable  Long technicianId) {
        serviceRequestService.assignTechnician(requestId, technicianId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned technician with id " + requestId));
    }



    // Endpoint # 12
    // get All Requests By Status
    @GetMapping("/status/{status}")
    public List<ServiceRequest> getRequestsByStatus(@PathVariable String status) {
        return serviceRequestService.findServiceRequestsByStatus(status);
    }

    // Endpoint  # 13
    // get All Requests By Date Range
    @GetMapping("/date-range/{startDate}/{endDate}")
    public List<ServiceRequest> getRequestsByDateRange(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        return serviceRequestService.findRequestsByDateRange(startDate, endDate);
    }



}
