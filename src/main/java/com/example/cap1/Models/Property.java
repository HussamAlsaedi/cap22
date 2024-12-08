package com.example.cap1.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long property_id;

    @NotEmpty(message = "Location is mandatory.")
    @Size(max = 50, message = "Location must not exceed 50 characters.")
    @Column(nullable = false, length = 50)
    private String property_location;

    @NotNull(message = "Price is mandatory.")
    @Min(value = 0, message = "Price must be a positive value.")
    @Column(nullable = false)
    private Double property_price;

    @NotEmpty(message = "Type is mandatory.")
    @Column(nullable = false, length = 10)
    private String property_type; // مثل "فيلا"، "شقة"، "مكتب"

    @NotNull(message = "Area is mandatory.")
    @Min(value = 1, message = "Area must be at least 1 square meter.")
    @Column(nullable = false)
    private Double property_area;

    @Column(nullable = false)
    private boolean property_isRented = false;

    @NotEmpty(message = "Description is mandatory.")
    @Size(max = 300, message = "Description must not exceed 300 characters.")
    @Column(nullable = false, length = 300)
    private String property_description;

    @Column(updatable = false, nullable = false)
    private LocalDateTime property_createdAt;

    @NotNull(message = "ownerId is mandatory.")
    @Column(columnDefinition = "int not null")
    private long property_ownerId;

    // يتم تنفيذ هذا قبل حفظ الكيان لأول مرة
    @PrePersist
    protected void onCreate() {
        this.property_createdAt = LocalDateTime.now();
    }

}
