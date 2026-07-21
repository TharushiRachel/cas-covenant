package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.controller.facilitypaper.FacilityPaperController;
import com.itechro.cas.model.dto.facility.FacilityLoadOptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class FPLoadOptionDTO implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperController.class);

    private boolean loadDirectorDetail;

    private boolean loadCompanyROA;

    private boolean loadComment;

    private boolean loadReviewerComment;

    private boolean loadDocument;

    private boolean loadCustomer;

    private boolean loadCustomerDocument;

    private boolean loadCustomerAddress;

    private boolean loadCustomerTelephone;

    private boolean loadCustomerIdentification;

    private boolean loadCustomerBankDetail;

    private boolean loadCustomerCribDetail;

    private boolean loadCustomerOtherBankFacility;

    private boolean loadUpcSectionData;

    private boolean loadFacilities;

    private boolean loadCreditRiskComments;

    private boolean loadCustomerRatings;

    private boolean loadCustomerCovenant;

    
    private boolean loadApprovedFacilityDocuments;

    private boolean loadShareHolderDetail;

    private boolean loadFacilityCovenant;

    private FacilityLoadOptionDTO facilityLoadOptionDTO;

    private boolean loadCribDetails;

    private boolean loadWalletShares;

    private boolean loadMDReviewComments;

    public boolean isLoadDirectorDetail() {
        return loadDirectorDetail;
    }

    public void loadDirectorDetail() {
        this.loadDirectorDetail = true;
    }

    public boolean isLoadCompanyROA() {
        return loadCompanyROA;
    }

    public void loadCompanyROA() {
        this.loadCompanyROA = true;
    }

    public boolean isLoadComment() {
        return loadComment;
    }

    public void loadComment() {
        this.loadComment = true;
    }

    public boolean isLoadDocument() {
        return loadDocument;
    }

    public void loadDocument() {
        this.loadDocument = true;
    }

    public boolean isLoadCustomer() {
        return loadCustomer;
    }

    public void loadCustomer() {
        this.loadCustomer = true;
    }

    public boolean isLoadCustomerDocument() {
        return loadCustomerDocument;
    }

    public void loadCustomerDocument() {
        this.loadCustomer = true;
        this.loadCustomerDocument = true;
    }

    public boolean isLoadCustomerCribDetail() {
        return loadCustomerCribDetail;
    }

    public void loadCustomerCribDetail() {
        this.loadCustomer = true;
        this.loadCustomerCribDetail = true;
    }

    public boolean isLoadCustomerOtherBankFacility() {
        return loadCustomerOtherBankFacility;
    }

    public void loadCustomerOtherBankFacility() {
        this.loadCustomer = true;
        this.loadCustomerOtherBankFacility = true;
    }

    public boolean isLoadUpcSectionData() {
        return loadUpcSectionData;
    }

    public void loadUpcSectionData() {
        this.loadUpcSectionData = true;
    }

    public boolean isLoadFacilities() {
        return loadFacilities;
    }

    public void loadFacilities() {
        this.loadFacilities = true;
    }

    public boolean isLoadCreditRiskComments() {
        return loadCreditRiskComments;
    }

    public void loadCreditRiskComments() {
        this.loadCreditRiskComments = true;
    }

    public FacilityLoadOptionDTO getFacilityLoadOptionDTO() {
        return facilityLoadOptionDTO;
    }

    public void setFacilityLoadOptionDTO(FacilityLoadOptionDTO facilityLoadOptionDTO) {
        this.facilityLoadOptionDTO = facilityLoadOptionDTO;
    }

    public boolean loadReviewerComment() {
        return this.loadReviewerComment = true;
    }

    public boolean isLoadReviewerComment() {
        return this.loadReviewerComment;
    }

    public void loadCustomerAddress() {
        this.loadCustomer = true;
        this.loadCustomerAddress = true;
    }

    public boolean isLoadCustomerAddress() {
        return this.loadCustomerAddress;
    }

    public void loadCustomerTelephone() {
        this.loadCustomer = true;
        this.loadCustomerTelephone = true;
    }

    public boolean isLoadCustomerTelephone() {
        return this.loadCustomerTelephone;
    }

    public void loadCustomerIdentification() {
        this.loadCustomer = true;
        this.loadCustomerIdentification = true;
    }

    public boolean isLoadCustomerIdentification() {
        return this.loadCustomerIdentification;
    }

    public void loadCustomerBankDetail() {
        this.loadCustomer = true;
        this.loadCustomerBankDetail = true;
    }

    public boolean isLoadCustomerBankDetail() {
        return this.loadCustomerBankDetail;
    }

    public void loadCustomerRelatedDetails() {
        this.loadCustomer = true;
        this.loadCustomerDocument = true;
        this.loadCustomerOtherBankFacility = true;
        this.loadCustomerCribDetail = true;
        this.loadCustomerAddress = true;
        this.loadCustomerTelephone = true;
        this.loadCustomerIdentification = true;
        this.loadCustomerBankDetail = true;
        this.loadCustomerRatings = true;
    } 

    public boolean isLoadCustomerRatings() {
        return loadCustomerRatings;
    }

    public void loadCustomerRatings() {
        this.loadCustomer = true;
        this.loadCustomerRatings = true;
    }

    public void loadCustomerCovenant() {
        this.loadCustomerCovenant = true;
    }

    public boolean isLoadCustomerCovenant() {
        return loadCustomerCovenant;
    }

    public boolean loadFacilityCovenant(){
        LOG.info("loadFacilityCovenant :{}",loadFacilityCovenant);
       return loadFacilityCovenant;
    }
    public boolean isLoadFacilityCovenant(){
        LOG.info("isLoadFacilityCovenant :{}",loadFacilityCovenant);
        return loadFacilityCovenant;
    }

    public void setLoadCustomerCovenant(boolean loadCustomerCovenant) {
        this.loadCustomerCovenant = loadCustomerCovenant;
    }

    public void setLoadFacilityCovenant(boolean loadFacilityCovenant){
        this.loadFacilityCovenant= loadFacilityCovenant;
    }

    public boolean isLoadApprovedFacilityDocuments() {
        return loadApprovedFacilityDocuments;
    }

    public void loadApprovedFacilityDocuments() {
        this.loadApprovedFacilityDocuments = true;
    }

    public boolean isLoadShareHolderDetail() {
        return loadShareHolderDetail;
    }

    public void loadShareHolderDetail() {
        this.loadShareHolderDetail = true;
    }

    public void loadCribDetails(){
        this.loadCribDetails = true;
    }
    public boolean isLoadCribDetails() {
        return loadCribDetails;
    }

    public void setLoadCribDetails(boolean loadCribDetails) {
        this.loadCribDetails = loadCribDetails;
    }

    public boolean isLoadWalletShares() {
        return loadWalletShares;
    }

    public void setLoadWalletShares(boolean loadWalletShares) {
        this.loadWalletShares = loadWalletShares;
    }

    public void loadWalletShares(){
        this.loadWalletShares = true;
    }

    public boolean isLoadMDReviewComments() {
        return loadMDReviewComments;
    }

    public void setLoadMDReviewComments(boolean loadMDReviewComments) {
        this.loadMDReviewComments = loadMDReviewComments;
    }

    public void loadLoadMDReviewComments(){
        this.loadMDReviewComments = true;
    }
}
