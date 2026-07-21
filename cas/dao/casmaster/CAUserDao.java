package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CAUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CAUserDao extends JpaRepository<CAUser, Integer> {

    List<CAUser> findByCaLevelLevelId(Integer levelId);

    List<CAUser> findByCaLevelLevelConfigId(Integer levelConfigId);

    List<CAUser> deleteByCaLevelLevelConfigId(Integer levelConfigId);

    List<CAUser> findByUserPoolUserName(String userName);

    List<CAUser> findByCaCommitteeCommitteeId(Integer committeeId);

}
