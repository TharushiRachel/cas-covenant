package com.itechro.cas.dao.lead;

import com.itechro.cas.model.domain.lead.DigitalApplicationFormDetail;
import org.springframework.stereotype.Repository;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface DigitalApplicationDetailDao extends JpaRepository<DigitalApplicationFormDetail, Integer> {
}
