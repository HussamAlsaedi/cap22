package com.example.cap1.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceRequest_id;

    @NotBlank(message = "Service type is mandatory")
    @Column(nullable = false)
    private String serviceRequest_serviceType; // Example: "Plumbing", "Electricity"

    @NotNull(message = "tenant_id is mandatory")
    @Column(nullable = false)
    private Long serviceRequest_tenant_id;

    @Column(nullable = true)
    private Long serviceRequest_technician_id;

    @Column(nullable = false)
    private String serviceRequest_status;


    @Column(updatable = false, nullable = false)
    private LocalDateTime serviceRequest_requestDate;

    @PrePersist
    protected void onCreate() {
        this.serviceRequest_requestDate = LocalDateTime.now();
    }
}
