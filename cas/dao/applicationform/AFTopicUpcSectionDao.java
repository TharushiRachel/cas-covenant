package com.itechro.cas.dao.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.AFTopicUpcSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFTopicUpcSectionDao extends JpaRepository<AFTopicUpcSection, Integer> {

    List<AFTopicUpcSection> findAllByUpcTemplateIDAndTopic_TopicIDAndStatus(Integer templateID, Integer topicID, AppsConstants.Status status);

    AFTopicUpcSection findByUpcTemplateIDAndUpcSectionIDAndStatus(Integer templateID,Integer sectionID, AppsConstants.Status status);
}
