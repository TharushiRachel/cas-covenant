package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CASCustomerCribReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CASCustomerCribReportDao extends JpaRepository<CASCustomerCribReport, Integer> {
}
