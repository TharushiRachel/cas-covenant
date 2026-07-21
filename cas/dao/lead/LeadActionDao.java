package com.itechro.cas.dao.lead;

import com.itechro.cas.model.domain.lead.LeadAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadActionDao extends JpaRepository<LeadAction, Integer> {
}
