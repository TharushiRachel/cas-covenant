package com.itechro.cas.dao.lead;
import com.itechro.cas.model.domain.lead.DigitalApplicationTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalApplicationTagDao extends JpaRepository<DigitalApplicationTag, Integer>{
    DigitalApplicationTag findByTagAndLeadId(String tag, Integer fpId);

    List<DigitalApplicationTag> findAllByLeadId(Integer fpId);
}
