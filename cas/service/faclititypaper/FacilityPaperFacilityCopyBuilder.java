package com.itechro.cas.service.faclititypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.facility.FacilitySecurityDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.model.dto.facility.FacilityAndSecurityCopyDTO;
import com.itechro.cas.model.dto.facilitypaper.CalculateExposureRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class FacilityPaperFacilityCopyBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperFacilityCopyBuilder.class);

    private FacilityPaper facilityPaper;

    private CredentialsDTO credentialsDTO;

    private FacilityPaperDao facilityPaperDao;

    private FacilityPaperService facilityPaperService;

    private FacilitySecurityDao facilitySecurityDao;

    private FacilityAndSecurityCopyDTO facilityDTO;

    private DocStorageDao docStorageDao;

    private Date date;

    private FacilityPaperDTO calculateFacilityPaperExposure;

    public FacilityPaperFacilityCopyBuilder(CredentialsDTO credentialsDTO, FacilityAndSecurityCopyDTO facilityAndSecurityCopyDTO) {
        this.credentialsDTO = credentialsDTO;
        this.facilityDTO = facilityAndSecurityCopyDTO;
        this.date = new Date();
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public FacilityPaperDao getFacilityPaperDao() {
        return facilityPaperDao;
    }

    public void setFacilityPaperDao(FacilityPaperDao facilityPaperDao) {
        this.facilityPaperDao = facilityPaperDao;
    }

    public FacilityPaperService getFacilityPaperService() {
        return facilityPaperService;
    }

    public void setFacilityPaperService(FacilityPaperService facilityPaperService) {
        this.facilityPaperService = facilityPaperService;
    }

    public FacilitySecurityDao getFacilitySecurityDao() {
        return facilitySecurityDao;
    }

    public void setFacilitySecurityDao(FacilitySecurityDao facilitySecurityDao) {
        this.facilitySecurityDao = facilitySecurityDao;
    }

    public FacilityAndSecurityCopyDTO getFacilityDTO() {
        return facilityDTO;
    }

    public void setFacilityDTO(FacilityAndSecurityCopyDTO facilityDTO) {
        this.facilityDTO = facilityDTO;
    }

    public DocStorageDao getDocStorageDao() {
        return docStorageDao;
    }

    public void setDocStorageDao(DocStorageDao docStorageDao) {
        this.docStorageDao = docStorageDao;
    }

    public FacilityPaperFacilityCopyBuilder buildInitialData() {
        this.facilityPaper = this.facilityPaperDao.getOne(facilityDTO.getFacilityPaperID());
        LOG.info("Facility copy initiated : {} : {}", facilityDTO, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperFacilityCopyBuilder buildFacilities() {

        Map<Integer, FacilitySecurity> facilitySecurityMap = new HashMap<>();
        Map<Integer, FacilitySecurity> addedFacilitySecuritiesMap = new HashMap<>();
        Integer maxFacilityOrder = this.facilityPaper.getFacilitySet().stream()
                .filter(facility -> {
                    return facility.getStatus().equals(AppsConstants.Status.ACT);})
                .collect(Collectors.toSet()).stream()
                .max(Comparator.comparingInt(Facility::getDisplayOrder))
                .orElse(null)
                .getDisplayOrder();

        Set<Facility> filteredFacilities = this.facilityPaper.getFacilitySet()
                .stream()
                .filter(facility -> {
                    return facility.getFacilityID().equals(facilityDTO.getFacilityID());
                })
                .collect(Collectors.toSet());

        for (Facility originalFacility : filteredFacilities) {
            for (FacilityFacilitySecurity originalFacilitySecurityFacilitySecurity : originalFacility.getFacilityFacilitySecurities()) {
                facilitySecurityMap.putIfAbsent(originalFacilitySecurityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID(), originalFacilitySecurityFacilitySecurity.getFacilitySecurity());
            }
        }


        for (Integer facilitySecurityID : facilitySecurityMap.keySet()) {
            FacilitySecurity facilitySecurity = new FacilitySecurity();
            FacilitySecurity mappedFacilitySecurity = facilitySecurityMap.get(facilitySecurityID);
            facilitySecurity.setSecurityDetail(mappedFacilitySecurity.getSecurityDetail());
            facilitySecurity.setSecurityAmount(mappedFacilitySecurity.getSecurityAmount());
            facilitySecurity.setStatus(mappedFacilitySecurity.getStatus());
            facilitySecurity.setIsCommonSecurity(mappedFacilitySecurity.getIsCommonSecurity());
            facilitySecurity.setCashAmount(mappedFacilitySecurity.getCashAmount());
            facilitySecurity.setIsCashSecurity(mappedFacilitySecurity.getIsCashSecurity());
            facilitySecurity.setSecurityCode(mappedFacilitySecurity.getSecurityCode());
            facilitySecurity.setSecurityCurrency(mappedFacilitySecurity.getSecurityCurrency());
            facilitySecurity.setCreatedDate(date);
            facilitySecurity.setCreatedBy(credentialsDTO.getUserName());
            facilitySecurity = facilitySecurityDao.saveAndFlush(facilitySecurity);
            addedFacilitySecuritiesMap.put(facilitySecurityID, facilitySecurity);
        }

        for (int i=0; i<facilityDTO.getNoOfCopies(); i++ ) {

            for (Facility facility : filteredFacilities) {
                Facility copyFacility = new Facility();
                copyFacility.setCreatedBy(credentialsDTO.getUserName());
                copyFacility.setCreatedDate(date);
                copyFacility.setCreditFacilityType(facility.getCreditFacilityType());
                copyFacility.setCreditFacilityTemplate(facility.getCreditFacilityTemplate());
                copyFacility.setCondition(facility.getCondition());
                copyFacility.setDisbursementAccNumber(facility.getDisbursementAccNumber());
                copyFacility.setDisplayOrder(facility.getDisplayOrder());
                copyFacility.setFacilityAmount(facility.getFacilityAmount());
                copyFacility.setExistingAmount(facility.getExistingAmount());
                copyFacility.setOriginalAmount(facility.getOriginalAmount());
                copyFacility.setFacilityCurrency(facility.getFacilityCurrency());
                copyFacility.setIsCooperate(facility.getIsCooperate());
                copyFacility.setIsOneOff(facility.getIsOneOff());
                copyFacility.setSeriesOfLoans(facility.getSeriesOfLoans());
                copyFacility.setRevolving(facility.getRevolving());
                copyFacility.setDirectFacility(facility.getDirectFacility());
                copyFacility.setIsNew(facility.getIsNew());
                copyFacility.setReduction(facility.getReduction());
                copyFacility.setEnhancement(facility.getEnhancement());
                copyFacility.setIsAnnual(facility.getIsAnnual());
                copyFacility.setIsAdditional(facility.getIsAdditional());
                copyFacility.setIsTermsAmended(facility.getIsTermsAmended());
                copyFacility.setIsReStructure(facility.getIsReStructure());
                copyFacility.setIsReSchedule(facility.getIsReSchedule());
                copyFacility.setSectorID(facility.getSectorID());
                copyFacility.setSubSectorID(facility.getSubSectorID());
                copyFacility.setCashFlowGenerationSectorID(facility.getCashFlowGenerationSectorID());
                copyFacility.setStatus(facility.getStatus());
                copyFacility.setOutstandingAmount(facility.getOutstandingAmount());
                copyFacility.setRepayment(facility.getRepayment());
                copyFacility.setPurpose(facility.getPurpose());
                copyFacility.setPurposeOfAdvance(facility.getPurposeOfAdvance());
                copyFacility.setFacilityRefCode(facility.getFacilityRefCode());
                copyFacility.setFacilityType(facility.getFacilityType());
                copyFacility.setRemark(facility.getRemark());

                for (FacilityVitalInfoData facilityVitalInfoData : facility.getFacilityVitalInfoData()) {
                    FacilityVitalInfoData copyFacilityVitalInfoData = new FacilityVitalInfoData();
                    copyFacilityVitalInfoData.setFacility(copyFacility);
                    copyFacilityVitalInfoData.setCftVitalInfoID(facilityVitalInfoData.getCftVitalInfoID());
                    copyFacilityVitalInfoData.setVitalInfoName(facilityVitalInfoData.getVitalInfoName());
                    copyFacilityVitalInfoData.setVitalInfoData(facilityVitalInfoData.getVitalInfoData());
                    copyFacilityVitalInfoData.setMandatory(facilityVitalInfoData.getMandatory());
                    copyFacilityVitalInfoData.setStatus(facilityVitalInfoData.getStatus());
                    copyFacilityVitalInfoData.setCreatedDate(date);
                    copyFacilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                    copyFacilityVitalInfoData.setVersion(0L);
                    copyFacilityVitalInfoData.setDisplayOrder(facilityVitalInfoData.getDisplayOrder());
                    copyFacility.addFacilityVitalInfoData(copyFacilityVitalInfoData);

                }

                for (FacilityFacilitySecurity originalFacilitySecurityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
                    FacilityFacilitySecurity copyFacilityFacilitySecurity = new FacilityFacilitySecurity();
                    copyFacilityFacilitySecurity.setFacility(copyFacility);
                    copyFacilityFacilitySecurity.setFacilitySecurity(addedFacilitySecuritiesMap.get(originalFacilitySecurityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID()));
                    copyFacilityFacilitySecurity.setIsCashSecurity(originalFacilitySecurityFacilitySecurity.getIsCashSecurity());
                    copyFacilityFacilitySecurity.setFacilitySecurityAmount(originalFacilitySecurityFacilitySecurity.getFacilitySecurityAmount());
                    copyFacilityFacilitySecurity.setStatus(originalFacilitySecurityFacilitySecurity.getStatus());
                    copyFacility.addFacilityFacilitySecurity(copyFacilityFacilitySecurity);
                }

                for (FacilityInterestRate originalFacilityInterestRate : facility.getFacilityInterestRates()) {
                    FacilityInterestRate copyFacilityInterestRate = new FacilityInterestRate();
                    copyFacilityInterestRate.setCftInterestRateID(originalFacilityInterestRate.getCftInterestRateID());
                    copyFacilityInterestRate.setRateName(originalFacilityInterestRate.getRateName());
                    copyFacilityInterestRate.setInterestRatingSubCategory(originalFacilityInterestRate.getInterestRatingSubCategory());
                    copyFacilityInterestRate.setIsEditable(originalFacilityInterestRate.getIsEditable());
                    copyFacilityInterestRate.setRateCode(originalFacilityInterestRate.getRateCode());
                    copyFacilityInterestRate.setUserComment(originalFacilityInterestRate.getUserComment());
                    copyFacilityInterestRate.setValue(originalFacilityInterestRate.getValue());
                    copyFacilityInterestRate.setIsDefault(originalFacilityInterestRate.getIsDefault());
                    copyFacilityInterestRate.setStatus(originalFacilityInterestRate.getStatus());
                    copyFacilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                    copyFacilityInterestRate.setCreatedDate(date);
                    copyFacility.addFacilityInterestRate(copyFacilityInterestRate);
                }

                for (FacilityOtherFacilityInformation originalFacilityOtherFacilityInformation : copyFacility.getOrderedFacilityOtherFacilityInformations()) {
                    if (originalFacilityOtherFacilityInformation.getStatus().equals(AppsConstants.Status.ACT)) {
                        FacilityOtherFacilityInformation facilityOtherFacilityInformation = new FacilityOtherFacilityInformation();
                        facilityOtherFacilityInformation.setCftOtherFacilityInfoID(originalFacilityOtherFacilityInformation.getCftOtherFacilityInfoID());
                        facilityOtherFacilityInformation.setOtherFacilityInfoName(originalFacilityOtherFacilityInformation.getOtherFacilityInfoName());
                        facilityOtherFacilityInformation.setOtherFacilityInfoCode(originalFacilityOtherFacilityInformation.getOtherFacilityInfoCode());
                        facilityOtherFacilityInformation.setOtherFacilityInfoFieldType(originalFacilityOtherFacilityInformation.getOtherFacilityInfoFieldType());
                        facilityOtherFacilityInformation.setDefaultValue(originalFacilityOtherFacilityInformation.getDefaultValue());
                        facilityOtherFacilityInformation.setDisplayOrder(originalFacilityOtherFacilityInformation.getDisplayOrder());
                        facilityOtherFacilityInformation.setCreatedBy(credentialsDTO.getUserName());
                        facilityOtherFacilityInformation.setCreatedDate(date);
                        facilityOtherFacilityInformation.setStatus(AppsConstants.Status.ACT);
                        copyFacility.addOtherFacilityInformation(facilityOtherFacilityInformation);
                    }
                }

                for (FacilityPurposeOfAdvance originalFacilityPurposeOfAdvance : facility.getFacilityPurposeOfAdvances()) {
                    FacilityPurposeOfAdvance copyFacilityPurposeOfAdvance = new FacilityPurposeOfAdvance();
                    copyFacilityPurposeOfAdvance.setPurposeOfAdvance(originalFacilityPurposeOfAdvance.getPurposeOfAdvance());
                    copyFacilityPurposeOfAdvance.setReferenceCode(originalFacilityPurposeOfAdvance.getReferenceCode());
                    copyFacilityPurposeOfAdvance.setReferenceDescription(originalFacilityPurposeOfAdvance.getReferenceDescription());
                    copyFacilityPurposeOfAdvance.setStatus(originalFacilityPurposeOfAdvance.getStatus());
                    copyFacilityPurposeOfAdvance.setCreatedBy(credentialsDTO.getUserName());
                    copyFacilityPurposeOfAdvance.setCreatedDate(date);
                    copyFacility.addFacilityPurposeOfAdvance(copyFacilityPurposeOfAdvance);
                }

                for (FacilityDocument originalFacilityDocument : facility.getFacilityDocuments()) {
                    FacilityDocument copyFacilityDocument = new FacilityDocument();

                    copyFacilityDocument.setRemark(originalFacilityDocument.getRemark());
                    copyFacilityDocument.setCftSupportDocID(originalFacilityDocument.getCftSupportDocID());
                    copyFacilityDocument.setDisplayOrder(originalFacilityDocument.getDisplayOrder());
                    copyFacilityDocument.setMandatory(originalFacilityDocument.getMandatory());
                    copyFacilityDocument.setSupportingDoc(originalFacilityDocument.getSupportingDoc());
                    if (originalFacilityDocument.getDocStorage() != null) {
                        DocStorage docStorage = new DocStorage();
                        docStorage.setFileName(originalFacilityDocument.getDocStorage().getFileName());
                        docStorage.setDescription(originalFacilityDocument.getDocStorage().getDescription());
                        docStorage.setDocument(originalFacilityDocument.getDocStorage().getDocument());
                        docStorage.setLastUpdatedDate(date);
                        copyFacilityDocument.setDocStorage(this.docStorageDao.save(docStorage));
                    }
                    copyFacilityDocument.setStatus(originalFacilityDocument.getStatus());
                    copyFacilityDocument.setCreatedBy(credentialsDTO.getUserName());
                    copyFacilityDocument.setCreatedDate(date);
                    copyFacility.addFacilityDocuments(copyFacilityDocument);
                }
                copyFacility.setDisplayOrder(maxFacilityOrder + i+1);
                this.facilityPaper.addFacility(copyFacility);
            }

        }

        return this;
    }

    public FacilityPaperFacilityCopyBuilder buildCalculateFacilityPaperExposure() {

        CalculateExposureRQ calculateExposureRQ = new CalculateExposureRQ();
        calculateExposureRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());
        calculateExposureRQ.setIsCooperate(facilityPaper.getIsCooperate());

        this.calculateFacilityPaperExposure = facilityPaperService.calculateFacilityPaperExposure(calculateExposureRQ, credentialsDTO);

        LOG.info("Facility copy calculation : {} : {}", calculateFacilityPaperExposure, credentialsDTO.getUserID());
        return this;
    }

    FacilityPaperFacilityCopyBuilder buildBaseFacilityData() throws AppsException {

        facilityPaper.setTotalDirectExposurePrevious(calculateFacilityPaperExposure.getTotalDirectExposurePrevious());
        facilityPaper.setTotalDirectExposureNew(calculateFacilityPaperExposure.getTotalDirectExposureNew());
        facilityPaper.setTotalIndirectExposurePrevious(calculateFacilityPaperExposure.getTotalIndirectExposurePrevious());
        facilityPaper.setTotalIndirectExposureNew(calculateFacilityPaperExposure.getTotalIndirectExposureNew());
        facilityPaper.setTotalExposurePrevious(calculateFacilityPaperExposure.getTotalExposurePrevious());
        facilityPaper.setTotalExposureNew(calculateFacilityPaperExposure.getTotalExposureNew());
        facilityPaper.setGroupTotalDirectExposurePrevious(calculateFacilityPaperExposure.getGroupTotalDirectExposurePrevious());
        facilityPaper.setGroupTotalDirectExposureNew(calculateFacilityPaperExposure.getGroupTotalDirectExposureNew());
        facilityPaper.setGroupTotalIndirectExposurePrevious(calculateFacilityPaperExposure.getGroupTotalIndirectExposurePrevious());
        facilityPaper.setGroupTotalIndirectExposureNew(calculateFacilityPaperExposure.getGroupTotalIndirectExposureNew());
        facilityPaper.setGroupTotalExposurePrevious(calculateFacilityPaperExposure.getGroupTotalExposurePrevious());
        facilityPaper.setGroupTotalExposureNew(calculateFacilityPaperExposure.getGroupTotalExposureNew());
        facilityPaper.setTotalDirectExposureExisting(calculateFacilityPaperExposure.getTotalDirectExposureExisting());
        facilityPaper.setTotalIndirectExposureExisting(calculateFacilityPaperExposure.getTotalIndirectExposureExisting());
        facilityPaper.setTotalExposureExisting(calculateFacilityPaperExposure.getTotalExposureExisting());
        facilityPaper.setExistingCashMargin(calculateFacilityPaperExposure.getExistingCashMargin());
        facilityPaper.setGroupExistingCashMargin(calculateFacilityPaperExposure.getGroupExistingCashMargin());
        facilityPaper.setGroupTotalDirectExposureExisting(calculateFacilityPaperExposure.getGroupTotalDirectExposureExisting());
        facilityPaper.setGroupTotalIndirectExposureExisting(calculateFacilityPaperExposure.getGroupTotalIndirectExposureExisting());
        facilityPaper.setGroupTotalExposureExisting(calculateFacilityPaperExposure.getGroupTotalExposureExisting());
        facilityPaper.setNetTotalExposureNew(calculateFacilityPaperExposure.getNetTotalExposureNew());
        facilityPaper.setNetTotalExposurePrevious(calculateFacilityPaperExposure.getNetTotalExposurePrevious());
        facilityPaper.setNetTotalExposureExisting(calculateFacilityPaperExposure.getNetTotalExposureExisting());
        facilityPaper.setGroupNetTotalExposureNew(calculateFacilityPaperExposure.getGroupNetTotalExposureNew());
        facilityPaper.setGroupNetTotalExposurePrevious(calculateFacilityPaperExposure.getGroupNetTotalExposurePrevious());
        facilityPaper.setGroupNetTotalExposureExisting(calculateFacilityPaperExposure.getGroupNetTotalExposureExisting());
        facilityPaper.setOutstandingCashMargin(calculateFacilityPaperExposure.getOutstandingCashMargin());
        facilityPaper.setProposedCashMargin(calculateFacilityPaperExposure.getProposedCashMargin());
        facilityPaper.setGroupOutstandingCashMargin(calculateFacilityPaperExposure.getGroupOutstandingCashMargin());
        facilityPaper.setGroupProposedCashMargin(calculateFacilityPaperExposure.getGroupProposedCashMargin());

        return this;

    }

    public FacilityPaper getCopyFacilities() {
        return facilityPaper;
    }

}
