package com.example.cap1.Services;

import com.example.cap1.ApiResponse.ApiException;
import com.example.cap1.Models.Contract;
import com.example.cap1.Models.ServiceRequest;
import com.example.cap1.Models.Technician;
import com.example.cap1.Models.Tenant;
import com.example.cap1.Repositories.ContractRepository;
import com.example.cap1.Repositories.ServiceRequestRepository;
import com.example.cap1.Repositories.TechnicianRepository;
import com.example.cap1.Repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final TenantRepository tenantRepository;
    private final TechnicianRepository technicianRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private EmailSenderJava emailSender;


    public List<ServiceRequest> findAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }

    public void addServiceRequest(ServiceRequest serviceRequest) {

        List<Contract> activeContracts = contractRepository.checkActiveContractByTenantIdAndEndDate(serviceRequest.getServiceRequest_tenant_id(), LocalDateTime.now());

        if (activeContracts.isEmpty()) {
            throw new ApiException("Tenant does not have an active contract.");
        }

        serviceRequest.setServiceRequest_status("Pending");

        serviceRequestRepository.save(serviceRequest);

    }

    public void updateServiceRequest(Long requestId, ServiceRequest serviceRequest) {

        ServiceRequest serviceRequestToUpdate = serviceRequestRepository.findServiceRequestById(requestId);
        Tenant tenant = tenantRepository.findTenantById(serviceRequest.getServiceRequest_tenant_id());
        if (serviceRequestToUpdate == null) {
            throw new ApiException("Service request does not exist.");
        }

        ServiceRequest findTenant = serviceRequestRepository.findServiceRequestById(tenant.getTenant_id());
        if (findTenant == null) {
            throw new ApiException("you are not authorized to update this service request.");
        }

        serviceRequestToUpdate.setServiceRequest_serviceType(serviceRequest.getServiceRequest_serviceType());
        serviceRequestRepository.save(serviceRequestToUpdate);

    }


    public void deleteServiceRequest(Long requestId) {
        ServiceRequest serviceRequest = serviceRequestRepository.findServiceRequestById(requestId);
        if (serviceRequest == null) {
            throw new ApiException("you are not authorized to delete this service request.");
        }
        serviceRequestRepository.delete(serviceRequest);
    }


    public void assignTechnician(Long requestId, Long technicianId) {

        ServiceRequest serviceRequest = serviceRequestRepository.findServiceRequestById(requestId);

        if (serviceRequest == null) {
            throw new ApiException("service request does not exist.");
        }

        Technician technician = technicianRepository.findTechnicianById(technicianId);

        if (technician == null) {
            throw new ApiException("You are not authorized to assign a technician.");
        }
        Technician check_Role = null;
        if (serviceRequest.getServiceRequest_serviceType().equalsIgnoreCase(technician.getTechnician_role()))
        {
            check_Role = technician;
        }
        if (check_Role == null) {
            throw new ApiException("you are not authorized to assign a technician.");
        }

        serviceRequest.setServiceRequest_technician_id(technician.getTechnician_id());

        serviceRequest.setServiceRequest_status("Accepted");

        serviceRequestRepository.save(serviceRequest);

        Tenant tenant = tenantRepository.findTenantById(serviceRequest.getServiceRequest_tenant_id());

        emailSender.sendEmail(tenant.getTenant_email(),
                "Successfully Accepted your Request",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " +tenant.getTenant_name()+ ",</p><br>" +
                        "<p>Successfully Accepted your Request with technician " + serviceRequest.getServiceRequest_technician_id() + "</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");

    }

    // this method find all service request depends on role of technician role
    public List<ServiceRequest> getAllServiceRequestsByRole(Long technicianId) {
        List<ServiceRequest> exRequestRole = new ArrayList<>();
        List<ServiceRequest> requests = serviceRequestRepository.findAll();

        Technician technician = technicianRepository.findTechnicianById(technicianId);

        for (ServiceRequest serviceRequest : requests) {
            if (serviceRequest.getServiceRequest_serviceType().equals(technician.getTechnician_role()) && serviceRequest.getServiceRequest_status().equals("Pending") ) {
                exRequestRole.add(serviceRequest);
            }
        }

       return exRequestRole;
    }


    public List<ServiceRequest> findServiceRequestsByStatus(String status) {
        return serviceRequestRepository.findServiceRequestsByStatus(status);
    }

    public List<ServiceRequest> findRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return serviceRequestRepository.findRequestsByDateRange(startDate, endDate);
    }


    }
