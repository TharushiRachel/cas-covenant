package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.RiskOpinionReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskOpinionReplyDao extends JpaRepository<RiskOpinionReply, Integer> {
}
