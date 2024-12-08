package com.example.cap1.Services;

import com.example.cap1.ApiResponse.ApiException;
import com.example.cap1.Models.Tenant;
import com.example.cap1.Repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final EmailSenderJava emailSender;

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public void addTenant(Tenant tenant) {
        tenantRepository.save(tenant);

        emailSender.sendEmail(tenant.getTenant_email(),
                "Welcome to Akenah platform",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + tenant.getTenant_name() + ",</p><br>" +
                        "<p>Thank you for registering on our website. Here are your details:</p><br>" +
                        "<ul style='font-size: 18px; color: green;'>" +
                        "<li>Your ID: " + tenant.getTenant_id() + "</li>" +
                        "<li>Username: " + tenant.getUsername() + "</li>" +
                        "<li>Email: " + tenant.getTenant_email() + "</li>" +
                        "</ul><br>" +
                        "<p>We are excited to have you onboard!</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");
    }

    public void updateTenant(Long tenant_id, Tenant updatedTenant) {
        Tenant oldTenant = tenantRepository.findTenantById(tenant_id);

        if (oldTenant == null) {
            throw new RuntimeException("Tenant not found");
        }

        oldTenant.setTenant_name(updatedTenant.getTenant_name());
        oldTenant.setTenant_email(updatedTenant.getTenant_email());
        oldTenant.setUsername(updatedTenant.getUsername());
        oldTenant.setTenant_password(updatedTenant.getTenant_password());
        tenantRepository.save(oldTenant);

        emailSender.sendEmail(oldTenant.getTenant_email(),
                "Successfully updated",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + oldTenant.getTenant_name() + ",</p><br>" +
                        "<p>Successfully updated tenant with Your Id " + tenant_id + "</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");
    }


    public void deleteTenant(Long tenant_id) {
        Tenant tenant = tenantRepository.findTenantById(tenant_id);
        if (tenant == null) {
            throw new ApiException("Tenant not found");
        }
        tenantRepository.deleteById(tenant_id);

        emailSender.sendEmail(tenant.getTenant_email(),
                "Successfully Deleted",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + tenant.getTenant_name() + ",</p><br>" +
                        "<p>Successfully Deleted tenant with Your Id " + tenant_id + "</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");
    }

    public void sendRequestToOwner(){

    }
}
