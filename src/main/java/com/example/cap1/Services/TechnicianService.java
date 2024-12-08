package com.example.cap1.Services;


import com.example.cap1.Models.Technician;
import com.example.cap1.Repositories.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private  final EmailSenderJava emailSender;
    public List<Technician> findAllTechnicians() {
        return technicianRepository.findAll();
    }

    public void addTechnician(Technician technician) {
        technicianRepository.save(technician);
        emailSender.sendEmail(technician.getTechnician_email(),
                "Welcome to Akenah platform",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + technician.getTechnician_name() + ",</p><br>" +
                        "<p>Thank you for registering on our website. Here are your details:</p><br>" +
                        "<ul style='font-size: 18px; color: green;'>" +
                        "<li>Your ID: " + technician.getTechnician_id() + "</li>" +
                        "<li>Username: " + technician.getUsername() + "</li>" +
                        "<li>Email: " + technician.getTechnician_email() + "</li>" +
                        "</ul><br>" +
                        "<p>We are excited to have you onboard!</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");

    }

    public void updateTechnician(Long technicianId, Technician updatedTechnician) {
        Technician oldTechnician = technicianRepository.findTechnicianById(technicianId);
        if (oldTechnician == null) {
            throw new RuntimeException("Technician not found");
        }

        oldTechnician.setTechnician_name(updatedTechnician.getTechnician_name());
        oldTechnician.setTechnician_email(updatedTechnician.getTechnician_email());
        oldTechnician.setUsername(updatedTechnician.getUsername());
        oldTechnician.setTechnician_password(updatedTechnician.getTechnician_password());
        oldTechnician.setTechnician_role(updatedTechnician.getTechnician_role());

        technicianRepository.save(oldTechnician);
    }

    public void deleteTechnician(Long technicianId) {
        Technician technician = technicianRepository.findTechnicianById(technicianId);
        if (technician == null) {
            throw new RuntimeException("Technician not found");
        }
        technicianRepository.delete(technician);
    }


}
