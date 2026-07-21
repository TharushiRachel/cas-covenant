package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CasCustomerOtherBankFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CASCustomerOtherBankFacilityDao extends JpaRepository<CasCustomerOtherBankFacility, Integer> {
}
