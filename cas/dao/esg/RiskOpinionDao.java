package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.esg.RiskOpinion;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RiskOpinionDao extends JpaRepository<RiskOpinion, Integer> {

    List<RiskOpinion> findAllByFacilityPaper(FacilityPaper facilityPaper);

    List<RiskOpinion> findAllByApplicationForm(ApplicationForm applicationForm);

    @Modifying
    @Transactional
    @Query(value = "UPDATE T_ESG_OPINION SET FACILITY_PAPER_ID = :fpId WHERE APPLICATION_FORM_ID = :afId", nativeQuery = true)
    void updateRiskOpinion(@Param("afId") Integer id, @Param("fpId") Integer value);
}
