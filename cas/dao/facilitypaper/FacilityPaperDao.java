package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacilityPaperDao extends JpaRepository<FacilityPaper, Integer> {

    FacilityPaper findByFpRefNumber(String refNumber);

    FacilityPaper findByFacilityPaperID(Integer facilityPaperID);

    FacilityPaper findByLeadID(Integer leadID);

    @Query(value = "SELECT COUNT(*) FROM T_FACILITY_SECURITY_DOCUMENT WHERE FACILITY_PAPER_ID = :fpId", nativeQuery = true)
    int getSecurityDocumentCountByFP(@Param("fpId") Integer facilityPaperID);

    @Query(value=" SELECT COUNT(*) FROM T_COMP_DEVIATIONS " +
            " WHERE FACILITY_PAPER_ID = :facilityPaperID AND IS_CHECKED = 'Y' AND STATUS = 'ACT' ",nativeQuery = true)
    Integer getDeviationCount(@Param("facilityPaperID") Integer facilityPaperID);
}
