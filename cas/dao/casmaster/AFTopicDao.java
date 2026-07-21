package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.AFTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFTopicDao extends JpaRepository<AFTopic, Integer> {

    AFTopic findByApproveStatusAndStatusAndTopicID(DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status, Integer topicID);

    List<AFTopic> findAllByApproveStatusAndStatus(DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status);

    AFTopic findByTopicCodeAndStatusAndApproveStatus(String topicCode, AppsConstants.Status status, DomainConstants.MasterDataApproveStatus approveStatus);
}
