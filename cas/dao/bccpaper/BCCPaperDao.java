package com.itechro.cas.dao.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.bccpaper.BoardCreditCommitteePaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCCPaperDao extends JpaRepository<BoardCreditCommitteePaper, Integer> {

    BoardCreditCommitteePaper findByFacilityPaper_FacilityPaperIDAndStatus(Integer facilityPaperID, AppsConstants.Status status);
}
