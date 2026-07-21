package com.itechro.cas.dao.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.esg.AnswerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDataDao extends JpaRepository<AnswerData, Integer> {

    List<AnswerData> findByAnnexure_AnnexureIdAndApplicationForm_ApplicationFormID(Integer annexureId, Integer applicationFormId);

    List<AnswerData> findByAnnexure_AnnexureIdAndFacilityPaper_FacilityPaperID(Integer annexureId, Integer facilityPaperId);

    @Query(value = "SELECT * FROM T_ESG_ANSWER_DATA WHERE ANNEXURE_ID = :annexureId", nativeQuery = true)
    List<AnswerData> findByAnnexureId(@Param("annexureId") Integer annexureId);
    
    List<AnswerData> findByApplicationForm_ApplicationFormIDAndStatus(Integer applicationFormId, AppsConstants.Status status);

    List<AnswerData> findByFacilityPaper_FacilityPaperIDAndStatus(Integer facilityPaperID, AppsConstants.Status status);

    List<AnswerData> findByApplicationForm_ApplicationFormID(Integer applicationFormID);

    List<AnswerData> findByFacilityPaper_FacilityPaperID(Integer facilityPaperID);
}
