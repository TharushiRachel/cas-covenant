package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UserDa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDaDao extends JpaRepository<UserDa,Integer> {

    UserDa findByApproveStatusAndStatusAndUserName(DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status, String userName);

    List<UserDa> findByStatus(AppsConstants.Status status);
}
