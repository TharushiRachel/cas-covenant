package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;

public class AFFacilityLoadOptionDTO implements Serializable {

    private boolean loadSecurities;

    private boolean loadFacilityInterestRate;

    private boolean loadFacilityVitalInfo;

    private boolean loadAFFacilityDocuments;

    public void loadAllData() {
        this.loadSecurities();
        this.loadFacilityInterestRate();
        this.loadFacilityVitalInfo();
        this.loadAFFacilityDocuments();
    }

    public boolean isLoadSecurities() {
        return loadSecurities;
    }

    public void loadSecurities() {
        this.loadSecurities = true;
    }

    public boolean isLoadFacilityInterestRate() {
        return loadFacilityInterestRate;
    }

    public boolean isLoadFacilityVitalInfo() {
        return loadFacilityVitalInfo;
    }

    public void loadFacilityVitalInfo() {
        this.loadFacilityVitalInfo = true;
    }

    public void loadFacilityInterestRate() {
        this.loadFacilityInterestRate = true;
    }

    public void loadAFFacilityDocuments() {
        this.loadAFFacilityDocuments = true;
    }

    public boolean isLoadAFFacilityDocuments() {
        return loadAFFacilityDocuments;
    }

}
