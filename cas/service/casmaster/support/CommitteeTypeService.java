package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.CommitteeTypeDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.casmaster.CommitteeType;
import com.itechro.cas.model.domain.casmaster.UserPool;
import com.itechro.cas.model.domain.das.DALimit;
import com.itechro.cas.model.domain.das.DALimitTemp;
import com.itechro.cas.model.dto.das.DADesignationCodeDTO;
import com.itechro.cas.model.dto.das.DALimitDTO;
import com.itechro.cas.model.dto.das.DALimitsWithApproveStatusDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class CommitteeTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaApproveHandler.class);

    @Autowired
    private CommitteeTypeDao committeeTypeDao;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DADesignationCodeDTO> getAllCommitteeDesignataions(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Get All DA Limits by :{} ", credentialsDTO);


        List<DADesignationCodeDTO> designationList = new ArrayList<>();
        List<CommitteeType> designationWIthALlDetails = new ArrayList<>();

        try {

            designationWIthALlDetails = committeeTypeDao.findAll();

            for (CommitteeType designation : designationWIthALlDetails) {

                DADesignationCodeDTO designationCodeDTO = new DADesignationCodeDTO();
                designationCodeDTO.setCode(designation.getCommitteeTypeName());
                designationCodeDTO.setDescription(designation.getCommitteeDescription());
                designationList.add(designationCodeDTO);
            }
        }
        catch (Exception e) {
            LOG.error("ERROR : ", e);
        }


        LOG.info("END : Get All DA Limits by :{} ");
        return designationList;
    }


}
