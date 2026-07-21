package com.itechro.cas.dao.lead;

import com.itechro.cas.model.domain.lead.LeadAppCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadAppCodeDao extends JpaRepository<LeadAppCode, Integer> {
    @Query(value = "select * from t_lead_app_code where lead_id = :leadID", nativeQuery = true)
    LeadAppCode findByLeadId(Integer leadID);
}
