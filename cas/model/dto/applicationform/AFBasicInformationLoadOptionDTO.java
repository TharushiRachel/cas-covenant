package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;

public class AFBasicInformationLoadOptionDTO implements Serializable {

    private boolean loadOwnershipDetails;

    private boolean loadCribAttachments;

    private boolean loadCribReports;

    private boolean loadOtherBankDetails;

    private boolean loadRiskRates;

    private boolean loadFinancialObligations;

    private boolean loadBorrowerGuarantor;

    //    This is form the Fincal Customer
    private boolean loadCustomerDetails;

    public void loadAllData() {
        this.loadOwnershipDetails();
        this.loadCribAttachments();
        this.loadCribReports();
        this.loadOtherBankDetails();
        this.loadRiskRates();
        this.loadFinancialObligations();
        this.loadBorrowerGuarantor();
        this.loadCustomerDetails();
    }

    public boolean isLoadOwnershipDetails() {
        return loadOwnershipDetails;
    }

    public void loadOwnershipDetails() {
        this.loadOwnershipDetails = true;
    }

    public boolean isLoadCribAttachments() {
        return loadCribAttachments;
    }

    public void loadCribAttachments() {
        this.loadCribAttachments = true;
    }

    public boolean isLoadCribReports() {
        return loadCribReports;
    }

    public void loadCribReports() {
        this.loadCribReports = true;
    }

    public boolean isLoadOtherBankDetails() {
        return loadOtherBankDetails;
    }

    public void loadOtherBankDetails() {
        this.loadOtherBankDetails = true;
    }

    public boolean isLoadRiskRates() {
        return loadRiskRates;
    }

    public void loadRiskRates() {
        this.loadRiskRates = true;
    }

    public boolean isLoadFinancialObligations() {
        return loadFinancialObligations;
    }

    public void loadFinancialObligations() {
        this.loadFinancialObligations = true;
    }

    public boolean isLoadBorrowerGuarantor() {
        return loadBorrowerGuarantor;
    }

    public void loadBorrowerGuarantor() {
        this.loadBorrowerGuarantor = true;
    }

    public boolean isLoadCustomerDetails() {
        return loadCustomerDetails;
    }

    public void loadCustomerDetails() {
        this.loadCustomerDetails = true;
    }

}
