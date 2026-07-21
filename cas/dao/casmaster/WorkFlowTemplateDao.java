package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkFlowTemplateDao extends JpaRepository<WorkFlowTemplate, Integer> {

    public WorkFlowTemplate findByWorkFlowTemplateNameAndStatusAndApproveStatus(String templateName, AppsConstants.Status status, DomainConstants.MasterDataApproveStatus approveStatus);

    public WorkFlowTemplate findByCodeAndStatusAndApproveStatus(String code, AppsConstants.Status status, DomainConstants.MasterDataApproveStatus approveStatus);

}
