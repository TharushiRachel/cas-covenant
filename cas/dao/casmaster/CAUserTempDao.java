package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CAUser;
import com.itechro.cas.model.domain.casmaster.CAUserTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CAUserTempDao extends JpaRepository<CAUserTemp, Integer> {

    List<CAUserTemp> findByCaLevelTempLevelConfigId(Integer levelConfigId);

    List<CAUserTemp> deleteByCaLevelTempLevelConfigId(Integer levelConfigId);

}
