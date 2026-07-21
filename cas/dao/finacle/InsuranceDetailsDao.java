package com.itechro.cas.dao.finacle;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.das.DALimitTemp;
import com.itechro.cas.model.domain.finacle.InsuranceValuationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceDetailsDao extends JpaRepository<InsuranceValuationDetails, Integer> {


}


