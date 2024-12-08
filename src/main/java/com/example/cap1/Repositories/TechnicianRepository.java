package com.example.cap1.Repositories;

import com.example.cap1.Models.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechnicianRepository extends JpaRepository<Technician, Integer> {

    @Query("select Tech from Technician Tech where Tech.technician_id= :technician_id")
    Technician findTechnicianById(@Param("technician_id") Long technician_id);}
