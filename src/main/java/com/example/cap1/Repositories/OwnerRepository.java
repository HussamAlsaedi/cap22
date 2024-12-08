package com.example.cap1.Repositories;

import com.example.cap1.Models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("select o from Owner o where o.owner_id= :ownerId")
    Owner findOwnerById(@Param("ownerId") Long ownerId);
}
