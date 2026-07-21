package com.itechro.cas.dao.facilitypaper.committee;

import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInquiryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommitteeInquiryMasterDao extends JpaRepository<CommitteeInquiryMaster, Integer> {
    List<CommitteeInquiryMaster> findByFacilityPaper_FacilityPaperID(Integer facilityPaperID);

}
