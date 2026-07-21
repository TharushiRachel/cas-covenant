package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EnvironmentalRiskDataDao extends JpaRepository<EnvironmentalRiskData,Integer> {

    void deleteByApplicationForm(ApplicationForm applicationForm);

    List<EnvironmentalRiskData> findAllByApplicationForm(ApplicationForm applicationForm);

    void deleteByFacilityPaper(FacilityPaper facilityPaper);

    List<EnvironmentalRiskData> findAllByFacilityPaper(FacilityPaper facilityPaper);

    @Modifying
    @Transactional
    @Query(value = "UPDATE T_ENVIRONMENTAL_RISK_DATA SET FACILITY_PAPER_ID = :fpId WHERE APPLICATION_FORM_ID = :afId", nativeQuery = true)
    void updateRiskDataAFId(@Param("afId") Integer id, @Param("fpId") Integer value);
}
