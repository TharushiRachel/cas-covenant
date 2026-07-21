package com.itechro.cas.service.faclititypaper;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInquiryMaster;
import com.itechro.cas.model.dto.casmaster.CAUserDTO;
import com.itechro.cas.model.dto.facilitypaper.committee.CommitteeInquiryMasterDTO;
import com.itechro.cas.model.security.CredentialsDTO;

import java.util.List;

public interface CommitteeInqService {

    List<CommitteeInquiryMasterDTO> saveUpdateCommitteeInquiry(CommitteeInquiryMasterDTO committeeInquiryMasterDTO, CredentialsDTO credentialsDTO) throws AppsException;

    List<CommitteeInquiryMasterDTO> getCommitteeInquiryByFacilityPaperId(Integer facilityPaperID) throws AppsException;

    CommitteeInquiryMasterDTO statusUpdateCommitteeInquiry(CommitteeInquiryMasterDTO committeeInquiryMasterDTO) throws AppsException;

    List<CAUserDTO> getCommitteeUsers(Integer facilityPaperID) throws AppsException;
}
