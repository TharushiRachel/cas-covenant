package com.itechro.cas.dao.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.BasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasicInformationDao extends JpaRepository<BasicInformation, Integer> {

    BasicInformation findByIdentificationNoAndStatusAndApplicationForm_ApplicationFormID(String identificationNo, AppsConstants.Status status,Integer applicationFormID);

    List<BasicInformation> findAllByApplicationForm_ApplicationFormID(Integer applicationFormID);
}
