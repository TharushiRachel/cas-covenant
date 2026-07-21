package com.itechro.cas.dao.bcc;

import com.itechro.cas.model.domain.bcc.FPBcc;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteePaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FPBccDao extends JpaRepository<FPBcc, Integer> {



   // FacilityPaper findByFpRefNumber(String refNumber);

  //  FacilityPaper findByfacilityPaperID(Integer facilityPaperID);
}
