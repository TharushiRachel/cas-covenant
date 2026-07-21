package com.itechro.cas.dao.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.esg.AnswerData;
import com.itechro.cas.model.domain.esg.EsgDocStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EsgDocStorageDao extends JpaRepository<EsgDocStorage, Integer> {

    List<EsgDocStorage> findByApplicationForm_ApplicationFormID(Integer applicationFormId);

    List<EsgDocStorage> findByFacilityPaper_FacilityPaperID(Integer facilityPaperID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE T_ESG_STORAGE SET FACILITY_PAPER_ID = :fpId WHERE APPLICATION_FORM_ID = :afId", nativeQuery = true)
    void updateESGStorageAFId(@Param("afId") Integer id, @Param("fpId") Integer value);
}
