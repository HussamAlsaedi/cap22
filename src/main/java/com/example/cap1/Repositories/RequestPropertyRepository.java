package com.example.cap1.Repositories;

import com.example.cap1.Models.RequestProperty;
import com.example.cap1.Models.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestPropertyRepository extends JpaRepository<RequestProperty,Integer> {

    @Query("select r from RequestProperty r where r.request_property_id= :request_property_id")
    RequestProperty findRequestPropertyById(@Param("request_property_id") Long request_property_id);

    @Query("select r from RequestProperty r ")
    RequestProperty findServiceRequests();

    @Query("select r from RequestProperty r where r.property_id= :property_id")
    RequestProperty findRequestPropertiesByProperty_id(@Param("property_id") Long property_id);



}
