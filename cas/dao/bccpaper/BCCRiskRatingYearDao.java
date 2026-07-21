package com.itechro.cas.dao.bccpaper;

import com.itechro.cas.model.domain.bccpaper.BccRiskRatingYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCCRiskRatingYearDao extends JpaRepository<BccRiskRatingYear, Integer> {
}
