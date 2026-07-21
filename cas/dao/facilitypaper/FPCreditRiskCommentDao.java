package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FPCreditRiskCommentDao extends JpaRepository<FPCreditRiskComment, Integer> {

}
