package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.applicationform.AFSecurityDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.domain.storage.DocStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FacilitySecuritiesReplicator extends CommandExecutor<ApplicationFormReplicateContext> {

    private static final Logger LOG = LoggerFactory.getLogger(FacilitySecuritiesReplicator.class);

    @Autowired
    private DocStorageDao docStorageDao;

    @Autowired
    private AFSecurityDao afSecurityDao;

    @Override
    public ApplicationFormReplicateContext execute(ApplicationFormReplicateContext context) throws AppsException {

        Map<Integer, AFSecurity> facilitySecurityMap = new HashMap<>();
        Map<Integer, AFSecurity> addedFacilitySecuritiesMap = new HashMap<>();


        for (AFFacility originalFacility : context.getCurrentApplicationForm().getOrderedAFFacilities().stream().filter(facility ->
                facility.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {
            AFFacility replicatedFacility = new AFFacility();
            replicatedFacility.setCreatedBy(context.getCredentialsDto().getUserName());
            replicatedFacility.setCreatedDate(context.getDate());
            replicatedFacility.setStatus(AppsConstants.Status.ACT);
            replicatedFacility.setDisplayOrder(originalFacility.getDisplayOrder());

            replicatedFacility.setCreditFacilityTemplate(originalFacility.getCreditFacilityTemplate());
            replicatedFacility.setCreditFacilityType(originalFacility.getCreditFacilityType());
            replicatedFacility.setFacilityRefCode(originalFacility.getFacilityRefCode());
            replicatedFacility.setFacilityCurrency(originalFacility.getFacilityCurrency());

            replicatedFacility.setDisbursementAccNumber(originalFacility.getDisbursementAccNumber());
            replicatedFacility.setPurpose(originalFacility.getPurpose());
            replicatedFacility.setRepayment(originalFacility.getRepayment());
            replicatedFacility.setCondition(originalFacility.getCondition());
            replicatedFacility.setRemark(originalFacility.getRemark());

            replicatedFacility.setIsNew(originalFacility.getIsNew());
            replicatedFacility.setDirectFacility(originalFacility.getDirectFacility());
            replicatedFacility.setIsOneOff(originalFacility.getIsOneOff());
            replicatedFacility.setSeriesOfLoans(originalFacility.getSeriesOfLoans());
            replicatedFacility.setRevolving(originalFacility.getRevolving());
            replicatedFacility.setReduction(originalFacility.getReduction());
            replicatedFacility.setEnhancement(originalFacility.getEnhancement());
            replicatedFacility.setIsCooperate(originalFacility.getIsCooperate());

            replicatedFacility.setSectorID(originalFacility.getSectorID());
            replicatedFacility.setSubSectorID(originalFacility.getSubSectorID());
            replicatedFacility.setPurposeOfAdvance(originalFacility.getPurposeOfAdvance());

            replicatedFacility.setExistingAmount(originalFacility.getExistingAmount());
            replicatedFacility.setFacilityAmount(originalFacility.getFacilityAmount());
            replicatedFacility.setOriginalAmount(originalFacility.getOriginalAmount());
            replicatedFacility.setOutstandingAmount(originalFacility.getOutstandingAmount());

            for (AFFacilityVitalInfoData afFacilityVitalInfoData : originalFacility.getOrderedAfFacilityVitalInfoData().stream().filter(afFacilityVitalInfoData ->
                    afFacilityVitalInfoData.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {
                AFFacilityVitalInfoData replicatedVitalInfoData = new AFFacilityVitalInfoData();
                replicatedVitalInfoData.setCftVitalInfoID(afFacilityVitalInfoData.getCftVitalInfoID());
                replicatedVitalInfoData.setVitalInfoName(afFacilityVitalInfoData.getVitalInfoName());
                replicatedVitalInfoData.setVitalInfoData(afFacilityVitalInfoData.getVitalInfoData());
                replicatedVitalInfoData.setMandatory(afFacilityVitalInfoData.getMandatory());
                replicatedVitalInfoData.setDisplayOrder(afFacilityVitalInfoData.getDisplayOrder());
                replicatedVitalInfoData.setStatus(afFacilityVitalInfoData.getStatus());
                replicatedVitalInfoData.setCreatedBy(context.getCredentialsDto().getUserName());
                replicatedVitalInfoData.setCreatedDate(context.getDate());
                replicatedFacility.addAfFacilityVitalInfoData(replicatedVitalInfoData);
            }

            for (AFFacilityInterestRate afFacilityInterestRate : originalFacility.getOrderedAfFacilityInterestRates().stream().filter(afFacilityInterestRate ->
                    afFacilityInterestRate.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {
                AFFacilityInterestRate replicatedInterestRate = new AFFacilityInterestRate();
                replicatedInterestRate.setCftInterestRateID(afFacilityInterestRate.getCftInterestRateID());
                replicatedInterestRate.setRateCode(afFacilityInterestRate.getRateCode());
                replicatedInterestRate.setUserComment(afFacilityInterestRate.getUserComment());
                replicatedInterestRate.setValue(afFacilityInterestRate.getValue());
                replicatedInterestRate.setIsDefault(afFacilityInterestRate.getIsDefault());
                replicatedInterestRate.setStatus(afFacilityInterestRate.getStatus());
                replicatedInterestRate.setCreatedBy(context.getCredentialsDto().getUserName());
                replicatedInterestRate.setCreatedDate(context.getDate());
                replicatedFacility.addAfFacilityInterestRates(replicatedInterestRate);
            }

            for (AFFacilityDocument afFacilityDocument : originalFacility.getOrderedAfFacilityDocuments().stream().filter(afFacilityDocument ->
                    afFacilityDocument.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {

                AFFacilityDocument replicatedFacilityDocument = new AFFacilityDocument();
                replicatedFacilityDocument.setCftSupportDocID(afFacilityDocument.getCftSupportDocID());
                replicatedFacilityDocument.setSupportingDoc(afFacilityDocument.getSupportingDoc());
                replicatedFacilityDocument.setRemark(afFacilityDocument.getRemark());
                if (afFacilityDocument.getDocStorage() != null) {
                    DocStorage docStorage = new DocStorage();
                    docStorage.setFileName(afFacilityDocument.getDocStorage().getFileName());
                    docStorage.setDescription(afFacilityDocument.getDocStorage().getDescription());
                    docStorage.setDocument(afFacilityDocument.getDocStorage().getDocument());
                    docStorage.setLastUpdatedDate(context.getDate());
                    replicatedFacilityDocument.setDocStorage(this.docStorageDao.save(docStorage));
                }
                replicatedFacilityDocument.setStatus(afFacilityDocument.getStatus());
                replicatedFacilityDocument.setCreatedBy(context.getCredentialsDto().getUserName());
                replicatedFacilityDocument.setCreatedDate(context.getDate());
                replicatedFacility.addAFFacilityDocuments(replicatedFacilityDocument);
            }


            for (AFFacilitySecurity afFacilitySecurity : originalFacility.getAfFacilitySecurities()) {
                facilitySecurityMap.putIfAbsent(afFacilitySecurity.getAfSecurity().getSecurityID(), afFacilitySecurity.getAfSecurity());
            }

            for (Integer securityID : facilitySecurityMap.keySet()) {
                AFSecurity afSecurity = new AFSecurity();
                AFSecurity mappedFacilitySecurity = facilitySecurityMap.get(securityID);
                afSecurity.setSecurityDetail(mappedFacilitySecurity.getSecurityDetail());
                afSecurity.setSecurityAmount(mappedFacilitySecurity.getSecurityAmount());
                afSecurity.setStatus(mappedFacilitySecurity.getStatus());
                afSecurity.setIsCommonSecurity(mappedFacilitySecurity.getIsCommonSecurity());
                afSecurity.setCashAmount(mappedFacilitySecurity.getCashAmount());
                afSecurity.setIsCashSecurity(mappedFacilitySecurity.getIsCashSecurity());
                afSecurity.setSecurityCode(mappedFacilitySecurity.getSecurityCode());
                afSecurity.setSecurityCurrency(mappedFacilitySecurity.getSecurityCurrency());
                afSecurity.setCreatedDate(context.getDate());
                afSecurity.setCreatedBy(context.getCredentialsDto().getUserName());
                afSecurity = afSecurityDao.saveAndFlush(afSecurity);
                addedFacilitySecuritiesMap.put(securityID, afSecurity);
            }

            for (AFFacilitySecurity afFacilitySecurity : originalFacility.getAfFacilitySecurities()) {
                AFFacilitySecurity replicatedFacilityFacilitySecurity = new AFFacilitySecurity();
                replicatedFacilityFacilitySecurity.setAfSecurity(addedFacilitySecuritiesMap.get(afFacilitySecurity.getAfSecurity().getSecurityID()));
                replicatedFacilityFacilitySecurity.setIsCashSecurity(afFacilitySecurity.getIsCashSecurity());
                replicatedFacilityFacilitySecurity.setFacilitySecurityAmount(afFacilitySecurity.getFacilitySecurityAmount());
                replicatedFacilityFacilitySecurity.setStatus(afFacilitySecurity.getStatus());
                replicatedFacility.addAFFacilitySecurity(replicatedFacilityFacilitySecurity);
            }

            context.getNewApplicationForm().addAFFacilities(replicatedFacility);

            LOG.info("Facility Copied {} : {} ", originalFacility.getCreditFacilityType().getFacilityTypeName(), originalFacility.getFacilityRefCode());
        }

        return context;
    }
}
