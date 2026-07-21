package com.itechro.cas.dao.applicationform.esg;

import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFAnnexureDataDao extends JpaRepository<AFAnnexureData, Integer> {
    List<AFAnnexureData> findByApplicationFormApplicationFormID(Integer applicationFormID);
}
