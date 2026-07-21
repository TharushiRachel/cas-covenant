package com.itechro.cas.dao.facilitypaper.committee;

import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInqReqResAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitteeInqReqResAuditDao extends JpaRepository<CommitteeInqReqResAudit, Integer> {

}
