package com.itechro.cas.dao.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFCribReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFCribReportDao extends JpaRepository<AFCribReport, Integer> {

    AFCribReport findByIdentificationNoAndStatus(String identificationNo, AppsConstants.Status status);
}
