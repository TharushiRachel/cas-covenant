package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureAnswerAud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureAnswerAudDao extends JpaRepository<AnnexureAnswerAud, Integer> {
}
