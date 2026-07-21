package com.itechro.cas.service.faclititypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.facilitypaper.committee.CommitteeInqReqResAuditDao;
import com.itechro.cas.dao.facilitypaper.committee.CommitteeInqReqResDao;
import com.itechro.cas.dao.facilitypaper.committee.CommitteeInquiryMasterDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInqReqRes;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInqReqResAudit;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInquiryMaster;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.casmaster.CAUserDTO;
import com.itechro.cas.model.dto.email.InquiryEmailDTO;
import com.itechro.cas.model.dto.facilitypaper.committee.CommitteeInqReqResAuditDTO;
import com.itechro.cas.model.dto.facilitypaper.committee.CommitteeInqReqResDTO;
import com.itechro.cas.model.dto.facilitypaper.committee.CommitteeInquiryMasterDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CommitteeInqServiceImpl implements CommitteeInqService{

    private final FacilityPaperDao facilityPaperDao;

    private final CommitteeInquiryMasterDao committeeInquiryMasterRepository;

    private final CommitteeInqReqResDao committeeInqReqResDao;

    private final CommitteeInqReqResAuditDao committeeInqReqResAuditDao;

    private final FacilityPaperJdbcDao facilityPaperJdbcDao;

    private final FacilityPaperService facilityPaperService;

    public CommitteeInqServiceImpl(FacilityPaperDao facilityPaperDao, CommitteeInquiryMasterDao committeeInquiryMasterRepository, CommitteeInqReqResDao committeeInqReqResDao, CommitteeInqReqResAuditDao committeeInqReqResAuditDao, FacilityPaperJdbcDao facilityPaperJdbcDao, FacilityPaperService facilityPaperService) {
        this.facilityPaperDao = facilityPaperDao;
        this.committeeInquiryMasterRepository = committeeInquiryMasterRepository;
        this.committeeInqReqResDao = committeeInqReqResDao;
        this.committeeInqReqResAuditDao = committeeInqReqResAuditDao;
        this.facilityPaperJdbcDao = facilityPaperJdbcDao;
        this.facilityPaperService = facilityPaperService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CommitteeInquiryMasterDTO> saveUpdateCommitteeInquiry(CommitteeInquiryMasterDTO committeeInquiryMasterDTO, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START | saveDataToAnnexure - ESGServiceImpl | request : {}", committeeInquiryMasterDTO);
        List<CommitteeInquiryMasterDTO> response = new ArrayList<>();
        try {
            CommitteeInquiryMaster committeeInquiryMaster = null;

            if (committeeInquiryMasterDTO.getCommitteeInquiryId() == 0) {
                committeeInquiryMaster = new CommitteeInquiryMaster();
            } else {
                committeeInquiryMaster = committeeInquiryMasterRepository.findById(committeeInquiryMasterDTO.getCommitteeInquiryId())
                        .orElseThrow(() -> new AppsException("Committee Inquiry Master not found for ID: " + committeeInquiryMasterDTO.getCommitteeInquiryId()));

                List<CommitteeInqReqRes> existingCommitteeInqReqResList = committeeInqReqResDao.findByCommitteeInquiryMaster_CommitteeInquiryId(
                        committeeInquiryMasterDTO.getCommitteeInquiryId());
                if (existingCommitteeInqReqResList != null && !existingCommitteeInqReqResList.isEmpty()) {
                    // If there are existing records, we will remove them to avoid duplicates
                    committeeInqReqResDao.deleteAll(existingCommitteeInqReqResList);
                }
            }

            FacilityPaper facilityPaper = facilityPaperDao.findById(committeeInquiryMasterDTO.getFacilityPaperID())
                    .orElseThrow(() -> new AppsException("Facility Paper not found for ID: " + committeeInquiryMasterDTO.getFacilityPaperID()));
            committeeInquiryMaster.setFacilityPaper(facilityPaper);
            committeeInquiryMaster.setInquiryStatus(committeeInquiryMasterDTO.getInquiryStatus());
            committeeInquiryMaster.setAssignUser(committeeInquiryMasterDTO.getAssignUser());
            committeeInquiryMaster.setAssignUserDisplayName(committeeInquiryMasterDTO.getAssignUserDisplayName());
            committeeInquiryMaster.setAssignUserWorkClass(committeeInquiryMasterDTO.getAssignUserWorkClass());
            committeeInquiryMaster.setReferenceInquiryId(committeeInquiryMasterDTO.getReferenceInquiryId());
            committeeInquiryMaster.setLastModifiedDate(new Date());
            committeeInquiryMaster.setStatus(AppsConstants.Status.ACT);

            List<CommitteeInqReqRes> committeeInqReqResList = new ArrayList<>();

            for (CommitteeInqReqResDTO committeeInqReqResDTO : committeeInquiryMasterDTO.getCommitteeInqReqResDTOList()) {

                String text = committeeInqReqResDTO.getInquiryRequestResponseText1();
                if (text != null) {
                    int maxRecordLength = 19750;
                    int chunkSize = 3950;

                    // Split the text into chunks of 19750 characters
                    for (int start = 0; start < text.length(); start += maxRecordLength) {
                        CommitteeInqReqRes committeeInqReqRes = new CommitteeInqReqRes();
                        committeeInqReqRes.setCommitteeInquiryMaster(committeeInquiryMaster);
                        committeeInqReqRes.setInquiryRequestResponseType(committeeInqReqResDTO.getInquiryRequestResponseType());
                        committeeInqReqRes.setCreatedBy(credentialsDTO.getUserName());
                        committeeInqReqRes.setCreatedDate(new Date());
                        committeeInqReqRes.setCreatedUserDisplayName(credentialsDTO.getDisplayName());

                        String chunk = text.substring(start, Math.min(start + maxRecordLength, text.length()));

                        // Split the chunk into 3950-character segments
                        committeeInqReqRes.setInquiryRequestResponseText1(chunk.substring(0, Math.min(chunkSize, chunk.length())));
                        if (chunk.length() > chunkSize) {
                            committeeInqReqRes.setInquiryRequestResponseText2(chunk.substring(chunkSize, Math.min(chunkSize * 2, chunk.length())));
                        }
                        if (chunk.length() > chunkSize * 2) {
                            committeeInqReqRes.setInquiryRequestResponseText3(chunk.substring(chunkSize * 2, Math.min(chunkSize * 3, chunk.length())));
                        }
                        if (chunk.length() > chunkSize * 3) {
                            committeeInqReqRes.setInquiryRequestResponseText4(chunk.substring(chunkSize * 3, Math.min(chunkSize * 4, chunk.length())));
                        }
                        if (chunk.length() > chunkSize * 4) {
                            committeeInqReqRes.setInquiryRequestResponseText5(chunk.substring(chunkSize * 4, Math.min(chunkSize * 5, chunk.length())));
                        }

                        committeeInqReqResList.add(committeeInqReqRes);
                    }
                }
            }

            committeeInquiryMaster.setCommitteeInqReqResList(committeeInqReqResList);

            committeeInquiryMasterRepository.save(committeeInquiryMaster);

            for (CommitteeInqReqRes committeeInqReqRes : committeeInqReqResList) {
                committeeInqReqRes.setCommitteeInquiryMaster(committeeInquiryMaster);
                saveDataToAudit(committeeInqReqRes); // Save to audit log
            }

            response = getCommitteeInquiryByFacilityPaperId(committeeInquiryMasterDTO.getFacilityPaperID());

            //send inquiry email
            if (committeeInquiryMasterDTO.getInquiryEmail() != null) {
                InquiryEmailDTO inquiryEmailDTO = committeeInquiryMasterDTO.getInquiryEmail();
                inquiryEmailDTO.setFacilityPaperId(committeeInquiryMasterDTO.getFacilityPaperID());
                this.facilityPaperService.sendCommitteeInquiryEmail(inquiryEmailDTO,credentialsDTO);
            }

            log.info("END | saveDataToAnnexure - ESGServiceImpl | response : {}", response);

        } catch (Exception e) {
            log.info("Exception occurred while saving Committee Inquiry Master: {}", e.getMessage());
            throw new AppsException("Failed to save committee inquiry");
        }
        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CommitteeInquiryMasterDTO> getCommitteeInquiryByFacilityPaperId(Integer facilityPaperID) throws AppsException {

        log.info("START | getCommitteeInquiryByFacilityPaperId - CommitteeInqServiceImpl | facilityPaperID : {}", facilityPaperID);
        List<CommitteeInquiryMasterDTO> result = new ArrayList<>();
        try{

        // Fetch all inquiries for the given FacilityPaperID
        List<CommitteeInquiryMaster> inquiryMasters = committeeInquiryMasterRepository
                .findByFacilityPaper_FacilityPaperID(facilityPaperID);

        if (inquiryMasters == null || inquiryMasters.isEmpty()) {
            log.warn("No inquiries found for Facility Paper ID: {}", facilityPaperID);
            List<CommitteeInquiryMasterDTO> emptyList = new ArrayList<>();
            //throw new AppsException("No inquiries found for the given Facility Paper ID.");
        }

        // Group inquiries by referenceInquiryId
        Map<Integer, List<CommitteeInquiryMaster>> inquiryMap = inquiryMasters.stream()
                .collect(Collectors.groupingBy(inq -> inq.getReferenceInquiryId() == null ? 0 : inq.getReferenceInquiryId()));

        // Process requests (referenceInquiryId = 0)
        List<CommitteeInquiryMaster> requests = inquiryMap.getOrDefault(0, new ArrayList<>());
        for (CommitteeInquiryMaster request : requests) {
            CommitteeInquiryMasterDTO requestDTO = new CommitteeInquiryMasterDTO(request);

            // Responses = records where referenceInquiryId = request.committeeInquiryId
            List<CommitteeInquiryMaster> responses = inquiryMap.getOrDefault(request.getCommitteeInquiryId(), new ArrayList<>());
            if (!responses.isEmpty()) {
                // Flatten responses into CommitteeInqReqResDTO and add to requestDTO's committeeInqReqResDTOList
                List<CommitteeInqReqResDTO> responseDTOs = responses.stream()
                        .flatMap(resp -> {
                            if (resp.getCommitteeInqReqResList() != null) {
                                return resp.getCommitteeInqReqResList().stream()
                                        .map(CommitteeInqReqResDTO::new);
                            }
                            return Stream.empty();
                        })
                        .collect(Collectors.toList());

                if (requestDTO.getCommitteeInqReqResDTOList() == null) {
                    requestDTO.setCommitteeInqReqResDTOList(new ArrayList<>());
                }
                requestDTO.getCommitteeInqReqResDTOList().addAll(responseDTOs);
            }

            // Combine inquiryRequestResponseText1 for records with the same committeeInquiryId
            if (requestDTO.getCommitteeInqReqResDTOList() != null) {
                Map<Integer, StringBuilder> combinedTextMap = new HashMap<>();
                for (CommitteeInqReqResDTO dto : requestDTO.getCommitteeInqReqResDTOList()) {
                    combinedTextMap
                            .computeIfAbsent(dto.getCommitteeInquiryId(), k -> new StringBuilder())
                            .append(dto.getInquiryRequestResponseText1() != null ? dto.getInquiryRequestResponseText1() : "");
                }

                // Update the DTOs with combined text
                for (CommitteeInqReqResDTO dto : requestDTO.getCommitteeInqReqResDTOList()) {
                    if (combinedTextMap.containsKey(dto.getCommitteeInquiryId())) {
                        dto.setInquiryRequestResponseText1(combinedTextMap.get(dto.getCommitteeInquiryId()).toString());
                    }
                }

            }

            if (requestDTO.getCommitteeInqReqResDTOList() != null && !requestDTO.getCommitteeInqReqResDTOList().isEmpty()) {
                List<CommitteeInqReqResDTO> filteredList = new ArrayList<>();
                Set<Integer> processedIds = new HashSet<>();

                for (CommitteeInqReqResDTO dto : requestDTO.getCommitteeInqReqResDTOList()) {
                    if (!processedIds.contains(dto.getCommitteeInquiryId())) {
                        filteredList.add(dto);
                        processedIds.add(dto.getCommitteeInquiryId());
                    }
                }

                requestDTO.setCommitteeInqReqResDTOList(filteredList);
            }
            result.add(requestDTO);
        }


        } catch (Exception e) {
            log.error("Exception occurred while fetching Committee Inquiries: {}", e.getMessage(), e);
            throw new AppsException("Failed to fetch Committee Inquiries for Facility Paper ID: " + facilityPaperID);
        }
        return result;
    }

    private CommitteeInqReqResAuditDTO saveDataToAudit(CommitteeInqReqRes committeeInqReqRes) throws AppsException {

        log.info("START | saveDataToAudit - CommitteeInqServiceImpl | request : {}", committeeInqReqRes);

        try {
            CommitteeInqReqResAudit committeeInqReqResAudit = new CommitteeInqReqResAudit();
            committeeInqReqResAudit.setCommitteeInquiryMaster(committeeInqReqRes.getCommitteeInquiryMaster());
            committeeInqReqResAudit.setCommitteeInqReqRes(committeeInqReqRes);
            committeeInqReqResAudit.setInquiryRequestResponseText1(committeeInqReqRes.getInquiryRequestResponseText1());
            committeeInqReqResAudit.setInquiryRequestResponseText2(committeeInqReqRes.getInquiryRequestResponseText2());
            committeeInqReqResAudit.setInquiryRequestResponseText3(committeeInqReqRes.getInquiryRequestResponseText3());
            committeeInqReqResAudit.setInquiryRequestResponseText4(committeeInqReqRes.getInquiryRequestResponseText4());
            committeeInqReqResAudit.setInquiryRequestResponseText5(committeeInqReqRes.getInquiryRequestResponseText5());
            committeeInqReqResAudit.setInquiryRequestResponseType(committeeInqReqRes.getInquiryRequestResponseType());
            committeeInqReqResAudit.setCreatedBy(committeeInqReqRes.getCreatedBy());
            committeeInqReqResAudit.setCreatedDate(committeeInqReqRes.getCreatedDate());
            committeeInqReqResAudit.setCreatedUserDisplayName(committeeInqReqRes.getCreatedUserDisplayName());

            committeeInqReqResAuditDao.save(committeeInqReqResAudit);

            CommitteeInqReqResAuditDTO reqResAuditDTO = new CommitteeInqReqResAuditDTO(committeeInqReqResAudit);

            log.info("END | saveDataToAudit - CommitteeInqServiceImpl | response : {}", reqResAuditDTO);
            return reqResAuditDTO;
        } catch (Exception e) {
            log.error("Exception occurred while saving data to audit: {}", e.getMessage(), e);
            throw new AppsException("Failed to save data to audit");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CommitteeInquiryMasterDTO statusUpdateCommitteeInquiry(CommitteeInquiryMasterDTO committeeInquiryMasterDTO) throws AppsException {

        log.info("START | confirmCommitteeInquiry - CommitteeInqServiceImpl | request : {}", committeeInquiryMasterDTO);

        CommitteeInquiryMasterDTO response = null;

        try {
            if (committeeInquiryMasterDTO.getCommitteeInquiryId() != null && committeeInquiryMasterDTO.getInquiryStatus().equals(AppsConstants.InquiryStatus.RES_CONFIRMED)) {
                log.info("Reference Inquiry ID is provided and Inquiry Status is RES_CONFIRMED. Updating the inquiry status.");

                CommitteeInquiryMaster committeeInquiryMaster = committeeInquiryMasterRepository
                        .findById(committeeInquiryMasterDTO.getCommitteeInquiryId())
                        .orElseThrow(() -> new AppsException("Committee Inquiry Master not found"));

                committeeInquiryMaster.setInquiryStatus(committeeInquiryMasterDTO.getInquiryStatus());
                committeeInquiryMasterRepository.save(committeeInquiryMaster);

                CommitteeInquiryMaster master = committeeInquiryMasterRepository
                        .findById(committeeInquiryMaster.getReferenceInquiryId())
                        .orElseThrow(() -> new AppsException("Committee Inquiry Master not found"));
                master.setInquiryStatus(AppsConstants.InquiryStatus.REQ_CLOSED);
                committeeInquiryMasterRepository.save(master);

                response = new CommitteeInquiryMasterDTO(committeeInquiryMaster);
            } else if (committeeInquiryMasterDTO.getCommitteeInquiryId() != null && committeeInquiryMasterDTO.getStatus().equals(AppsConstants.Status.INA)) {
                log.info("Reference Inquiry ID is provided and Status is INA. Updating the inquiry status to INACTIVE.");

                CommitteeInquiryMaster committeeInquiryMaster = committeeInquiryMasterRepository
                        .findById(committeeInquiryMasterDTO.getCommitteeInquiryId())
                        .orElseThrow(() -> new AppsException("Committee Inquiry Master not found"));

                committeeInquiryMaster.setStatus(AppsConstants.Status.INA);
                committeeInquiryMasterRepository.save(committeeInquiryMaster);

                response = new CommitteeInquiryMasterDTO(committeeInquiryMaster);
            }
        } catch (Exception e) {
            log.error("Exception occurred while updating Committee Inquiry status: {}", e.getMessage(), e);
            throw new AppsException("Failed to update Committee Inquiry status");
        }

        log.info("END | confirmCommitteeInquiry - CommitteeInqServiceImpl | response : {}", response);
        return response;
    }

    @Override
    public List<CAUserDTO> getCommitteeUsers(Integer facilityPaperID) throws AppsException {

        log.info("START | getCommitteeUsers - CommitteeInqServiceImpl | facilityPaperID : {}", facilityPaperID);

        List<CAUserDTO> caUserDTOList = new ArrayList<>();
        try {
            caUserDTOList = facilityPaperJdbcDao.getCommitteeUsersByFacilityPaper(facilityPaperID);
        } catch (Exception e) {
            log.error("Exception occurred while fetching committee users: {}", e.getMessage(), e);
            throw new AppsException("Failed to fetch committee users for Facility Paper ID: " + facilityPaperID);
        }

        log.info("END | getCommitteeUsers - CommitteeInqServiceImpl | response size : {}", caUserDTOList.size());

        return caUserDTOList;
    }
}
