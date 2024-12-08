package com.example.cap1.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenant_id;

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 20 characters")
    @Column(nullable = false, length = 20)
    private String tenant_name;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    @Column(nullable = false, unique = true, length = 60)
    private String tenant_email;

    @NotEmpty(message = "username is mandatory")
    @Size(min = 1, max = 10, message = "username must be between 1 and 10 characters")
    @Column(nullable = false, unique = true, length = 10)
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one digit, and one special character"
    )    @Column(nullable = false, length =100)
    private String tenant_password;

    @Column(updatable = false, nullable = false)
    private LocalDateTime tenant_createdAt;

    @PrePersist
    protected void onCreate() {
        this.tenant_createdAt = LocalDateTime.now();
    }

}
