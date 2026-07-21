package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.DomainConstants.MasterDataApproveStatus;
import com.itechro.cas.model.domain.casmaster.UpcTemplateData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpcTemplateDataDao extends JpaRepository<UpcTemplateData, Integer> {

    List<UpcTemplateData> findAllByUpcSection_UpcSectionID(Integer upcSectionID);

}
