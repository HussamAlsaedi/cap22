package com.example.cap1.Services;

import com.example.cap1.ApiResponse.ApiException;
import com.example.cap1.Models.Owner;
import com.example.cap1.Models.Property;
import com.example.cap1.Models.RequestProperty;
import com.example.cap1.Models.Tenant;
import com.example.cap1.Repositories.OwnerRepository;
import com.example.cap1.Repositories.PropertyRepository;
import com.example.cap1.Repositories.RequestPropertyRepository;
import com.example.cap1.Repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestPropertyService {
    @Autowired
    private final RequestPropertyRepository requestPropertyRepository;
    @Autowired
    private final PropertyRepository propertyRepository;
    @Autowired
    private final TenantRepository tenantRepository;
    @Autowired
    private final EmailSenderJava emailSender;
    @Autowired
    private final OwnerRepository ownerRepository;

    public List<RequestProperty> getAll() {
        return requestPropertyRepository.findAll();
    }

    public void addRequestProperty(RequestProperty requestProperty) {
        Property property = propertyRepository.findPropertyById(requestProperty.getProperty_id());
        Tenant tenant = tenantRepository.findTenantById(requestProperty.getTenant_id());
        Owner owner  = ownerRepository.findOwnerById(property.getProperty_ownerId());

        if (property == null) {
            throw new ApiException("Property not found");
        }
        if (tenant == null) {
            throw new ApiException("Tenant not found");
        }


        requestPropertyRepository.save(requestProperty);

        emailSender.sendEmail(owner.getOwner_email(),
                "Request property added",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + tenant.getTenant_name() + ",</p><br>" +
                        "<p>tenant with Id " + requestProperty.getTenant_id() + " want your property id "+ requestProperty.getProperty_id() +" </p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");
    }

    public void updateRequestProperty(long requestPropertyId, RequestProperty requestProperty) {
        RequestProperty oldRequestProperty1 = requestPropertyRepository.findRequestPropertyById(requestPropertyId);
        if (oldRequestProperty1 == null) {
            throw new ApiException("RequestProperty not found");
        }
        oldRequestProperty1.setProperty_id(requestProperty.getRequest_property_id());
        oldRequestProperty1.setTenant_id(requestProperty.getTenant_id());

        requestPropertyRepository.save(oldRequestProperty1);
    }

    public void deleteRequestProperty(long requestPropertyId) {
        RequestProperty Property1 = requestPropertyRepository.findRequestPropertyById(requestPropertyId);
        if (Property1 == null) {
            throw new ApiException("RequestProperty not found");
        }
        requestPropertyRepository.delete(Property1);
    }


}
