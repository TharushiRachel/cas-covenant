package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPCustomerClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FPCustomerClassificationDao extends JpaRepository<FPCustomerClassification, Integer> {

    List<FPCustomerClassification> findByFacilityPaperFacilityPaperIDAndCustomerCasCustomerID(Integer facilityPaperId, Integer customerId);
}
