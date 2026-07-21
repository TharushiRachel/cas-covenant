package com.itechro.cas.service.acae;

import com.itechro.cas.dao.acae.jdbc.ACAEStatusInquiryJdbcDAO;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryByDateRangeRQ;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryByResubmittedRQ;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryBySolIdRQ;
import com.itechro.cas.model.dto.acae.response.ACAEDateRangeInquiryDTO;
import com.itechro.cas.model.dto.acae.response.ACAEInquiryByResubmittedDTO;
import com.itechro.cas.model.dto.acae.response.ACAEInquiryBySolIdDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ACAEStatusInquiryService {//StatusMonitorBean
    private static final Logger LOG = LoggerFactory.getLogger(ACAEStatusInquiryService.class);
    @Autowired
    private ACAEStatusInquiryJdbcDAO acaeStatusInquiryJdbcDAO;

    @Autowired
    private ACAECommonService acaeCommonService;

    public Page<ACAEDateRangeInquiryDTO> getInquiryByDateRangeService(ACAEInquiryByDateRangeRQ acaeInquiryByDateRangeRQ, CredentialsDTO credentialsDTO) {

        Page<ACAEDateRangeInquiryDTO> acaeDateRangeInquiryDTOList;
        String workClass =credentialsDTO.getUpmGroupCode() ;
        String reqWorkClass="";
        if (workClass.equals("80")) {
            reqWorkClass = "74";
        } else if (workClass.equals("74")) {
            reqWorkClass = "73";
        } else if (workClass.equals("73")) {
            reqWorkClass = "72";
        } else if (workClass.equals("72")) {
            reqWorkClass = "71";
        } else if (workClass.equals("71")) {
            reqWorkClass = "50";
        }
        acaeDateRangeInquiryDTOList = acaeStatusInquiryJdbcDAO.getInquiryByDateRangeRepository(acaeInquiryByDateRangeRQ,reqWorkClass);
        return acaeDateRangeInquiryDTOList;
    }

    public List<ACAEInquiryBySolIdDTO> getInquiryBySolIdsService(ACAEInquiryBySolIdRQ acaeInquiryBySolIdRQ, CredentialsDTO credentialsDTO) {
        List<ACAEInquiryBySolIdDTO> AACAEInquiryBySolIdList = new ArrayList<>();
        if(acaeInquiryBySolIdRQ.getSolIdList() !=null || !acaeInquiryBySolIdRQ.getSolIdList().equals("")){
            ArrayList searchSolList = new ArrayList();
            searchSolList = acaeCommonService.getRecordListAsArrayList(acaeInquiryBySolIdRQ.getSolIdList().trim());
            if (searchSolList.size() == 0) {
                LOG.error("err","Please enter Sol id(s) to search.");
            } else {
                AACAEInquiryBySolIdList = acaeStatusInquiryJdbcDAO.getInquiryBySolIdsRepository(searchSolList);
            }
        }else{
            LOG.info("Please enter comma seperated SOL Ids to perform Search function.");
        }
        return AACAEInquiryBySolIdList;
    }

    public Page<ACAEInquiryByResubmittedDTO> getInquiryByResubmittedService(ACAEInquiryByResubmittedRQ acaeInquiryByResubmittedRQ, CredentialsDTO credentialsDTO) {
        Page<ACAEInquiryByResubmittedDTO> ACAEInquiryByResubmittedDTOList = new Page<>();
        if(acaeInquiryByResubmittedRQ.getSolIdList() !=null || !acaeInquiryByResubmittedRQ.getSolIdList().equals("")){
            ArrayList searchSolList = new ArrayList();
            searchSolList = acaeCommonService.getRecordListAsArrayList(acaeInquiryByResubmittedRQ.getSolIdList().trim());
            if (searchSolList.size() == 0) {
                LOG.error("err","Please enter Sol id(s) to search.");
            } if(acaeInquiryByResubmittedRQ.getReportType() ==null ||acaeInquiryByResubmittedRQ.equals("") ){
                LOG.error("Please select the type of the report required.");
            }else {
                acaeInquiryByResubmittedRQ.setSolList(searchSolList);
                ACAEInquiryByResubmittedDTOList = acaeStatusInquiryJdbcDAO.getInquiryByResubmittedRepository(acaeInquiryByResubmittedRQ);
            }
        }else{
            LOG.error("Please enter comma seperated SOL Ids to perform Search function.");
        }
        return ACAEInquiryByResubmittedDTOList;
    }
}
