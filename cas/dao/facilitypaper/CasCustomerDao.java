package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CasCustomerDao extends JpaRepository<CASCustomer, Integer> {

    Set<CASCustomer> findAllByCustomer_CustomerIDAndStatus(Integer customerID, AppsConstants.Status status);

    @Query(value = "SELECT * FROM t_cas_customer WHERE customer_id = :customerId AND facility_paper_id = :facilityPaperId AND STATUS = 'ACT'", nativeQuery = true)
    CASCustomer findByCustomerIdAndFacilityPaperId(@Param("customerId") Integer customerId, @Param("facilityPaperId") Integer facilityPaperId);

    List<CASCustomer> findAllByFacilityPaperFacilityPaperID(Integer facilityPaperID);
}
