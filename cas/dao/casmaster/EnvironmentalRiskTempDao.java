package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.EnvironmentalRiskTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EnvironmentalRiskTempDao extends JpaRepository<EnvironmentalRiskTemp, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM T_ENVIRONMENTAL_RISK_TEMP WHERE PARENT_ID = :parentId", nativeQuery = true)
    void deleteByParentId(@Param("parentId") Integer parentId);
}
