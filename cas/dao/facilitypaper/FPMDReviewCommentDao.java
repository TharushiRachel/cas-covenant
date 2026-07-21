package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPMDReviewComment;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FPMDReviewCommentDao extends JpaRepository<FPMDReviewComment, Integer> {

    List<FPMDReviewComment> findAllByFacilityPaper(FacilityPaper facilityPaper);
}
