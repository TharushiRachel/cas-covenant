package com.itechro.cas.model.dto.facility;

import java.io.Serializable;

public class FacilityLoadOptionDTO implements Serializable {

    private boolean loadFacilityDocument;

    private boolean loadFacilityInterestRate;

    private boolean loadFacilityVitalInfo;

    private boolean loadOtherFacilityInfo;

    private boolean loadFacilityPurposeOfAdvance;

    private boolean loadSecurities;

    private boolean loadFacilityRentalInfo;

    private boolean loadFacilityCustomInfo;

    public void loadAllData() {
        this.loadSecurities();
        this.loadFacilityDocument();
        this.loadFacilityInterestRate();
        this.loadFacilityPurposeOfAdvance();
        this.loadFacilityVitalInfo();
        this.loadOtherFacilityInfo();
        this.loadFacilityRentalInfo();
        this.loadFacilityCustomInfo();
    }

    public boolean isLoadFacilityDocument() {
        return loadFacilityDocument;
    }

    public void loadFacilityDocument() {
        this.loadFacilityDocument = true;
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

    public boolean isLoadFacilityPurposeOfAdvance() {
        return loadFacilityPurposeOfAdvance;
    }

    public void loadFacilityPurposeOfAdvance() {
        this.loadFacilityPurposeOfAdvance = true;
    }

    public boolean isLoadSecurities() {
        return loadSecurities;
    }

    public void loadSecurities() {
        this.loadSecurities = true;
    }

    public boolean isLoadOtherFacilityInfo() {
        return loadOtherFacilityInfo;
    }

    public void loadOtherFacilityInfo() {
        this.loadOtherFacilityInfo = true;
    }

    public boolean isLoadFacilityRentalInfo() {
        return loadFacilityRentalInfo;
    }

    public void loadFacilityRentalInfo() {
        this.loadFacilityRentalInfo = true;
    }

    public boolean isLoadFacilityCustomInfo(){
        return loadFacilityCustomInfo;
    }

    public void loadFacilityCustomInfo(){
        this.loadFacilityCustomInfo = true;
    }
}
