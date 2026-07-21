package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureQuestionTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureQuestionTempDao extends JpaRepository<AnnexureQuestionTemp, Integer> {
}
