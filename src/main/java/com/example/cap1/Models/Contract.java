package com.example.cap1.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "contract_type In ('شراء','تأجير')")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contract_id;

    @NotNull(message = "startDate Id is mandatory")
    @Column(nullable = false)
    private LocalDateTime contract_start_date;

    @NotNull(message = "endDate Id is mandatory")
    @Column(nullable = false)
    private LocalDateTime contract_end_date;

    @NotEmpty(message = "endDate Id is mandatory")
    @Column(nullable = false)
    @Pattern(regexp = "شراء|تأجير", message = "contract type must be 'شراء او تاجير '")
    private String contract_type;

    @NotNull(message = "Rental Amount is mandatory")
    @Positive(message = "Rental Amount must be Positive number")
    @Column(nullable = false)
    private double contract_amount;

    @Column(nullable = false)
    private Long contract_owner_id;

    @Column(nullable = false)
    private Long contract_property_id;


    @Column(nullable = false)

    private Long contract_tenant_id;

    @Column(updatable = false, nullable = false)
    private LocalDateTime contract_createdAt;

    @PrePersist
    protected void onCreate() {
        this.contract_createdAt = LocalDateTime.now();
    }

}
