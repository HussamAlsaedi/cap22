package com.example.cap1.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rating
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rating_id;

    @Column(nullable = false)
    private Long tenant_id;

    @Column(nullable = false)
    private Long property_id;

    @Column(nullable = false)
    @Min(value = 1, message = "Property rating must be at least 1")
    @Max(value = 5, message = "Property rating must not exceed 5")    private Integer property_rating;

    @Column(nullable = false)
    private String description;


}
