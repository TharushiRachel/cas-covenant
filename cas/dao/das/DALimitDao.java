package com.itechro.cas.dao.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.das.DALimit;
import com.itechro.cas.model.domain.das.DALimitTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DALimitDao extends JpaRepository<DALimit, Integer> {

    List<DALimit> findAllByDesignationIdAndStatus(Integer designationId, AppsConstants.Status status);

    DALimit findByDesignationIdAndColumnIdAndStatus(Integer designationId, Integer columnId, AppsConstants.Status status);

    List<DALimit> findAllByStatus(AppsConstants.Status status);
}
