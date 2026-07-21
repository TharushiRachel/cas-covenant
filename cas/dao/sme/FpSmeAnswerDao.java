package com.itechro.cas.dao.sme;

import com.itechro.cas.model.domain.sme.FpSmeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FpSmeAnswerDao extends JpaRepository<FpSmeAnswer, Integer> {

List<FpSmeAnswer> findByFacilityPaper_FacilityPaperIDAndCreatedBy(Integer facilityPaperID, String createdBy);

    List<FpSmeAnswer> findByFacilityPaper_FacilityPaperID(Integer facilityPaperID);
}
