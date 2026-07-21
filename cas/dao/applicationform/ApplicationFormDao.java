package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationFormDao extends JpaRepository<ApplicationForm, Integer> {

    ApplicationForm findByAfRefNumber(String refNumber);

    ApplicationForm findByLeadID(Integer leadID);
}
