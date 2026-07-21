package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UpmGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpmGroupDao extends JpaRepository<UpmGroup, Integer> {

    List<UpmGroup> findAllByApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus);
}
