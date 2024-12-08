package com.example.cap1.Controllers;

import com.example.cap1.ApiResponse.ApiResponse;
import com.example.cap1.Models.Contract;
import com.example.cap1.Services.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/v1/Contract")
@RequiredArgsConstructor
public class ContractController {

        @Autowired
    private ContractService contractService;

    @GetMapping("/get")
    public  List<Contract> getAllContracts() {
       return contractService.findAllContracts();

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addContract(@RequestBody Contract contract, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }
        contractService.addContract(contract);
       return ResponseEntity.ok(new ApiResponse("Successfully added  Contract"));
    }

    @PutMapping("/update/{ContractId}")
    public ResponseEntity<ApiResponse> updateContract(@PathVariable Long ContractId, @RequestBody @Valid Contract updatedContract, Errors errors) {
       if (errors.hasErrors()) {
           String errorMessage = errors.getAllErrors().get(0).getDefaultMessage();
           return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
       }
        contractService.updateContract(ContractId, updatedContract);
        return ResponseEntity.status(200).body(new ApiResponse("successfully updated  Contract with ID " + ContractId));
    }

    @DeleteMapping("/delete/{ContractId}")
    public ResponseEntity<ApiResponse> deleteContract(@PathVariable Long ContractId) {
        contractService.deleteContract(ContractId);
        return ResponseEntity.status(200).body(new ApiResponse("successfully deleted  Contract with ID " + ContractId));
    }

     // Endpoint to searchContractsByStartDateRang #1
    @GetMapping("/get-all-Contract-inRange/{DateFrom}/{DateTo}")
    public List<Contract> searchContractsByStartDateRange(@PathVariable LocalDateTime DateFrom, @PathVariable LocalDateTime DateTo) {
       return contractService.findContractsByStartDateRange(DateFrom,DateTo);
    }

    // Endpoint find all active contracts #2
    @GetMapping("/active")
    public List<Contract> findActiveContracts() {
        return  contractService.findActiveContracts();
    }

    // Endpoint find  Contracts By tenantId   #3
    @GetMapping("/get-ContractByTenantId/{tenantId}")
    public Contract findContractsByTenantId(@PathVariable Long tenantId) {
      return  contractService.findContractsByTenantId(tenantId);
    }

    // Endpoint find  Contracts By OwnerId   #4
    @GetMapping("/get-ContractByOwnerId/{ownerId}")
    public Contract findContractsByOwnerId(@PathVariable Long ownerId) {
        return  contractService.findContractsByOwnerId(ownerId);
    }

    // Endpoint to find all contract's amount in range  #5
    @GetMapping("/get-AllContractsByAmountRange/{min}/{max}")
    public List<Contract> find(@PathVariable Double min, @PathVariable Double max) {
        return contractService.findContractsByAmountRange(min,max);
    }
}
