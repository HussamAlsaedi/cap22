package com.example.cap1.Repositories;

import com.example.cap1.Models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("select R from Contract R where R.contract_id= :Contract_id")
    Contract findContractById(@Param("Contract_id") Long Contract_id);

    @Query("select R from Contract R where R.contract_tenant_id= :tenantId and R.contract_start_date <=:now")
    List<Contract> checkActiveContractByTenantIdAndEndDate(@Param("tenantId") Long tenantId, @Param("now") LocalDateTime now);


    @Query("select R from Contract R where R.contract_start_date between  :DateFrom and :DateTo")
    List<Contract> findByContract_startDateBetween(@Param("DateFrom") LocalDateTime DateFrom, @Param("DateTo") LocalDateTime DateTo);

    @Query("select R from  Contract R where R.contract_end_date >= :now")
    List<Contract> findActiveContracts(@Param("now") LocalDateTime now);

    @Query("select R from Contract R where R.contract_tenant_id= :tenantId")
    Contract findContractsByTenantId(@Param("tenantId") Long tenantId);

    @Query("select R from Contract R where R.contract_owner_id= :ownerId")
    Contract findContractsByOwnerId(@Param("ownerId") Long ownerId);

    @Query("select R from  Contract R where  R.contract_amount between :min and :max")
    List<Contract> findContractsByAmountRange(@Param("min") Double min, @Param("max") Double max);


}
