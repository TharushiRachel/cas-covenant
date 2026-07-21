package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureQuestionDao extends JpaRepository<AnnexureQuestion, Integer> {

}
