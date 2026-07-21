package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.WorkFlowTemplateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowTemplateDataDao extends JpaRepository<WorkFlowTemplateData, Integer> {

    List<WorkFlowTemplateData> findAllByUpmGroup_UpmGroupID(Integer upmGroupID);

    List<WorkFlowTemplateData> findAllByNextUPMGroup_UpmGroupID(Integer upmGroupID);

    List<WorkFlowTemplateData> findAllByPreviousUPMGroup_UpmGroupID(Integer upmGroupID);
}
