package com.example.cap1.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class RequestProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long request_property_id;

    @NotNull(message = "property_id is mandatory.")
    @Column(nullable = false)
    private Long property_id;

    @NotNull(message = "tenant_id is mandatory.")
    @Column(nullable = false)
    private Long tenant_id;


}
