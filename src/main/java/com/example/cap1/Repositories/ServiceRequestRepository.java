package com.example.cap1.Repositories;

import com.example.cap1.Models.Contract;
import com.example.cap1.Models.ServiceRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface  ServiceRequestRepository extends JpaRepository<ServiceRequest,Long> {

    @Query("select s from ServiceRequest s where s.serviceRequest_id= :serviceRequest_id")
    ServiceRequest findServiceRequestById(@Param("serviceRequest_id") Long serviceRequest_id);

    @Query("select s from ServiceRequest s where  s.serviceRequest_serviceType=:role")
    List<ServiceRequest> getAllServiceRequestsByRole(@Param("role") Long role);


    @Query("select s from ServiceRequest s where  s.serviceRequest_status=:status")
    List<ServiceRequest> findServiceRequestsByStatus(@Param("status") String  status);


   // findRequestsByDateRange
    @Query("select s from ServiceRequest s where s.serviceRequest_requestDate between  :DateFrom and :DateTo")
    List<ServiceRequest> findRequestsByDateRange(@Param("DateFrom") LocalDateTime DateFrom,
                                                 @Param("DateTo") LocalDateTime DateTo);

}
