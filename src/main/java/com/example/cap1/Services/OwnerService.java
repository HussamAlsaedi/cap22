package com.example.cap1.Services;

import com.example.cap1.ApiResponse.ApiException;
import com.example.cap1.Models.Owner;
import com.example.cap1.Repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final EmailSenderJava emailSender;


    // Retrieve all Owners
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public void addOwner(Owner owner) {

        ownerRepository.save(owner);
        emailSender.sendEmail(owner.getOwner_email(),
                "Welcome to Akenah platform",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + owner.getOwner_name() + ",</p><br>" +
                        "<p>Thank you for registering on our website. Here are your details:</p><br>" +
                        "<ul>" +
                        "<li>Your ID: " + owner.getOwner_id() + "</li>" +
                        "<li>Username: " + owner.getUsername() + "</li>" +
                        "<li>Email: " + owner.getOwner_email() + "</li>" +
                        "</ul><br>" +
                        "<p>We are excited to have you onboard!</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");

    }

    //  Update an existing owner by ownerId
    public void updateOwner(Long ownerId, Owner ownerUpdate) {

        Owner oldOwner = ownerRepository.findOwnerById(ownerId);

        if (oldOwner == null) {
            throw new ApiException("Owner not found");
        }

        oldOwner.setOwner_name(ownerUpdate.getOwner_name());
        oldOwner.setOwner_email(ownerUpdate.getOwner_email());
        oldOwner.setOwner_password(ownerUpdate.getOwner_password());
          ownerRepository.save(oldOwner);

        emailSender.sendEmail(oldOwner.getOwner_email(),
                "Successfully updated",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + oldOwner.getOwner_name() + ",</p><br>" +
                        "<p>Successfully updated tenant with Your Id " + ownerId + "</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");

    }

    // Delete an existing owner by ownerId
    public void deleteOwner(Long ownerId) {

        Owner Owner = ownerRepository.findOwnerById(ownerId);

        if (Owner == null) {
            throw new ApiException("Owner not found");
        }
        ownerRepository.deleteById(ownerId);

        emailSender.sendEmail(Owner.getOwner_email(),
                "Successfully Deleted",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + Owner.getOwner_name() + ",</p><br>" +
                        "<p>Successfully Deleted tenant with Your Id " + ownerId + "</p><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>");
    }
}
