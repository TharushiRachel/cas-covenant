package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.applicationform.AFCustomerDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.BasicInformation;
import com.itechro.cas.service.applicationform.command.replicate.support.AFCustomerReplicateBuilder;
import com.itechro.cas.service.applicationform.command.replicate.support.BasicInformationReplicateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BasicInformationReplicator extends CommandExecutor<ApplicationFormReplicateContext> {

    private static final Logger LOG = LoggerFactory.getLogger(BasicInformationReplicator.class);

    @Autowired
    AFCustomerDao afCustomerDao;

    @Autowired
    DocStorageDao docStorageDao;

    @Override
    public ApplicationFormReplicateContext execute(ApplicationFormReplicateContext context) throws AppsException {

        for (BasicInformation basicInformation : context.getCurrentApplicationForm().getOrderedBasicInformation().stream().filter(basicInformation ->
                basicInformation.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {
            BasicInformation replicatedBasicInformation = new BasicInformation();

            replicatedBasicInformation.setStatus(AppsConstants.Status.ACT);
            replicatedBasicInformation.setCreatedBy(context.getCredentialsDto().getUserName());
            replicatedBasicInformation.setCreatedDate(context.getDate());
            replicatedBasicInformation.setTitle(basicInformation.getTitle());
            replicatedBasicInformation.setType(basicInformation.getType());
            replicatedBasicInformation.setNameWithInitials(basicInformation.getNameWithInitials());
            replicatedBasicInformation.setInitialRepresentation(basicInformation.getInitialRepresentation());
            replicatedBasicInformation.setNameOfBusiness(basicInformation.getNameOfBusiness());
            replicatedBasicInformation.setRegistrationNo(basicInformation.getRegistrationNo());
            replicatedBasicInformation.setConstitution(basicInformation.getConstitution());
            replicatedBasicInformation.setDateOfIncorporate(basicInformation.getDateOfIncorporate());
            replicatedBasicInformation.setDateOfCommencement(basicInformation.getDateOfCommencement());
            replicatedBasicInformation.setDateOfRegistration(basicInformation.getDateOfRegistration());
            replicatedBasicInformation.setNatureOfBusiness(basicInformation.getNatureOfBusiness());
            replicatedBasicInformation.setNoOfBusinessYears(basicInformation.getNoOfBusinessYears());
            replicatedBasicInformation.setCitizenship(basicInformation.getCitizenship());
            replicatedBasicInformation.setPrivateAddress(basicInformation.getPrivateAddress());
            replicatedBasicInformation.setOfficialAddress(basicInformation.getOfficialAddress());
            replicatedBasicInformation.setBusinessAddress(basicInformation.getBusinessAddress());
            replicatedBasicInformation.setTelNumber(basicInformation.getTelNumber());
            replicatedBasicInformation.setEmailAddress(basicInformation.getEmailAddress());
            replicatedBasicInformation.setDateOfBirth(basicInformation.getDateOfBirth());
            replicatedBasicInformation.setPlaceOfBirth(basicInformation.getPlaceOfBirth());
            replicatedBasicInformation.setCivilStatus(basicInformation.getCivilStatus());
            replicatedBasicInformation.setNationality(basicInformation.getNationality());
            replicatedBasicInformation.setIdentificationNo(basicInformation.getIdentificationNo());
            replicatedBasicInformation.setEmployment(basicInformation.getEmployment());
            replicatedBasicInformation.setEmployer(basicInformation.getEmployer());
            replicatedBasicInformation.setHighestEduAchievement(basicInformation.getHighestEduAchievement());
            replicatedBasicInformation.setPosition(basicInformation.getPosition());
            replicatedBasicInformation.setNoOfYearsEmployment(basicInformation.getNoOfYearsEmployment());
            replicatedBasicInformation.setIdentificationType(basicInformation.getIdentificationType());
            replicatedBasicInformation.setCapitalAuthorized(basicInformation.getCapitalAuthorized());
            replicatedBasicInformation.setCapitalIssued(basicInformation.getCapitalIssued());
            replicatedBasicInformation.setCapitalPaidUp(basicInformation.getCapitalPaidUp());
            replicatedBasicInformation.setPrimaryInformation(basicInformation.getPrimaryInformation());
            replicatedBasicInformation.setCustomerID(basicInformation.getCustomerID());
            if (basicInformation.getPrimaryInformation() == AppsConstants.YesNo.Y) {
                context.getNewApplicationForm().setFormType(basicInformation.getType());
            }

            AFCustomerReplicateBuilder afCustomerReplicateBuilder = new AFCustomerReplicateBuilder(context.getCredentialsDto());
            afCustomerReplicateBuilder.setDate(context.getDate());
            afCustomerReplicateBuilder.setCurrentAFCustomer(basicInformation.getAfCustomer());
            afCustomerReplicateBuilder.setAfCustomerDao(afCustomerDao);
            replicatedBasicInformation.setAfCustomer(
                    afCustomerReplicateBuilder
                            .buildInitialAFCustomer()
                            .buildAfCustomerAddress()
                            .buildAFCustomerIdentifications()
                            .buildAfCustomerTelephones()
                            .buildAFBankDetails()
                            .saveAfCustomer()
                            .getReplicatedAFCustomer());


            BasicInformationReplicateBuilder basicInformationReplicateBuilder = new BasicInformationReplicateBuilder(context.getCredentialsDto());
            basicInformationReplicateBuilder.setGetReplicateApplicationFromFormRQ(context.getReplicateApplicationFromFormRQ());
            basicInformationReplicateBuilder.setDocStorageDao(docStorageDao);
            basicInformationReplicateBuilder
                    .init(basicInformation, replicatedBasicInformation, context.getDate())
                    .buildRiskRating()
                    .buildFinancialObligation()
                    .buildOtherBankDetails()
                    .buildBorrowerGuarantorDetails()
                    .buildOwnerShipDetails()
                    .buildCribAttachments()
                    .buildCribReports();

            context.getNewApplicationForm().addBasicInformation(replicatedBasicInformation);
            LOG.info("Basic Information Copied {} ", basicInformation.getIdentificationNo());
        }

        return context;
    }

}
