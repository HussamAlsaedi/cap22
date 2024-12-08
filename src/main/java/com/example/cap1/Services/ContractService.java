package com.example.cap1.Services;


import com.example.cap1.Models.Contract;
import com.example.cap1.Models.Owner;
import com.example.cap1.Models.Property;
import com.example.cap1.Models.Tenant;
import com.example.cap1.Repositories.ContractRepository;
import com.example.cap1.Repositories.OwnerRepository;
import com.example.cap1.Repositories.PropertyRepository;
import com.example.cap1.Repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final OwnerRepository ownerRepository;
    private final TenantRepository tenantRepository;
    private final PropertyRepository propertyRepository;
    @Autowired
    private final EmailSenderJava emailSender;
    public List<Contract> findAllContracts() {
        return contractRepository.findAll();
    }


    public void addContract(Contract contract) {

        Owner owner = ownerRepository.findOwnerById(contract.getContract_owner_id());
        Tenant tenant = tenantRepository.findTenantById(contract.getContract_tenant_id());
        Property property = propertyRepository.findPropertyById(contract.getContract_property_id());

        if (property == null) {
            throw new RuntimeException("Property not found");
        }
        if (owner == null) {
            throw new RuntimeException("Owner not found");
        }
        if (tenant == null) {
            throw new RuntimeException("Tenant not found");
        }



        property.setProperty_isRented(true);
        contractRepository.save(contract);
        emailSender.sendEmail(
                tenant.getTenant_email(),
                "Owner created contract",
                "<html>" +
                        "<body style='background-color: green; font-size: 18px; color: white;'>" +
                        "<div style='background-color: white; border: 4px solid green; padding: 10px; color: black;'>" +
                        "<p>Hello Mr. " + tenant.getTenant_name() + ",</p><br>" +
                        "<p>Your contract details are as follows:</p>" +
                        "<ul>" +
                        "<li><strong>Contract ID:</strong> " + contract.getContract_id() + "</li>" +
                        "<li><strong>contract type:</strong> " + contract.getContract_type() + "</li>" +
                        "<li><strong>Start Date:</strong> " + contract.getContract_start_date() + "</li>" +
                        "<li><strong>End Date:</strong> " + contract.getContract_end_date() + "</li>" +
                        "<li><strong>Property ID:</strong> " + contract.getContract_property_id() + "</li>" +
                        "</ul><br>" +
                        "<p>Best regards,</p>" +
                        "<p>The Website Team</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"
        );
    }

    public void updateContract(Long ContractId, Contract updatedContract) {

        Contract oldContract = contractRepository.findContractById(ContractId);

        if (oldContract == null) {
            throw new RuntimeException(" contract not found");
        }

        oldContract.setContract_type(updatedContract.getContract_type());
        oldContract.setContract_start_date(updatedContract.getContract_start_date());
        oldContract.setContract_end_date(updatedContract.getContract_end_date());
        oldContract.setContract_amount(updatedContract.getContract_amount());
        oldContract.setContract_owner_id(updatedContract.getContract_owner_id());
        oldContract.setContract_tenant_id(updatedContract.getContract_tenant_id());

        contractRepository.save(oldContract);

    }


    public void deleteContract(Long ContractId) {
        Contract contract = contractRepository.findContractById(ContractId);

        if (contract == null) {
            throw new RuntimeException("Contract not found");
        }

        contractRepository.deleteById(ContractId);
    }

    public List<Contract> findContractsByStartDateRange(LocalDateTime DateFrom, LocalDateTime DateTo) {
        return contractRepository.findByContract_startDateBetween(DateFrom, DateTo);
    }

    public List<Contract> findActiveContracts() {
        return contractRepository.findActiveContracts(LocalDateTime.now());
    }

    public Contract findContractsByTenantId(Long tenantId) {
        return contractRepository.findContractsByTenantId(tenantId);
    }

    public Contract findContractsByOwnerId(Long ownerId) {
        return contractRepository.findContractsByOwnerId(ownerId);
    }

    public List<Contract> findContractsByAmountRange(Double min, Double max) {
        return contractRepository.findContractsByAmountRange(min, max);
    }

}
