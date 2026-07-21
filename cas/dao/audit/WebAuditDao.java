package com.itechro.cas.dao.audit;

import com.itechro.cas.model.domain.audit.WebAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAuditDao extends JpaRepository<WebAudit, Integer> {
}
