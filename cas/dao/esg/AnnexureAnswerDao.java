package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureAnswerDao extends JpaRepository<AnnexureAnswer, Integer> {

}
