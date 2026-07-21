package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureQuestionAud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureQuestionAudDao extends JpaRepository<AnnexureQuestionAud, Integer> {
}
