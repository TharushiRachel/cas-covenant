package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPUpcSectionData;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FPUpcSectionDataDao extends JpaRepository<FPUpcSectionData, Integer> {

    @Query(value = "SELECT * FROM T_FP_UPC_SECTION_DATA\n" +
            "WHERE STATUS = 'ACT'\n" +
            "AND FACILITY_PAPER_ID = :facilityPaperID", nativeQuery = true)
    List<FPUpcSectionData> findByFacilityPaperFacilityPaperID(Integer facilityPaperID);
}
