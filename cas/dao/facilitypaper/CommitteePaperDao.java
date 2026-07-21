package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.CommitteePaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitteePaperDao extends JpaRepository<CommitteePaper, Integer> {

    List<CommitteePaper> findByCommitteeID(Integer committeeID);

   // FacilityPaper findByFpRefNumber(String refNumber);

  //  FacilityPaper findByfacilityPaperID(Integer facilityPaperID);
}
