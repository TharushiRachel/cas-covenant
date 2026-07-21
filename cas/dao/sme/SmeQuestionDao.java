package com.itechro.cas.dao.sme;

import com.itechro.cas.model.domain.sme.SmeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmeQuestionDao extends JpaRepository<SmeQuestion, Integer> {
}
