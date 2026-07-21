package com.itechro.cas.dao.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.das.DALimitTemp;
import com.itechro.cas.model.dto.das.DALimitsWithApproveStatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DALimitTempDao extends JpaRepository<DALimitTemp, Integer> {

    List<DALimitTemp> findAllByDesignationIdAndStatus(Integer designationId, AppsConstants.Status status);

    DALimitTemp findByDesignationIdAndColumnIdAndStatus(Integer designationId, Integer columnId, AppsConstants.Status status);

    List<DALimitTemp> findAllByStatus(AppsConstants.Status status);

}


