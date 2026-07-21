package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;

public class AFLoadOptionsDTO implements Serializable {

    private boolean loadComments;

    private boolean loadBasicInformation;

    private boolean loadDocument;

    private boolean loadFacilities;

    private boolean loadAFTopics;

    private boolean loadAFStatusHistory;

    private AFFacilityLoadOptionDTO afFacilityLoadOptionDTO;

    private AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO;

    public void loadAllData() {
        this.loadComments();
        this.loadBasicInformation();
        this.loadDocument();
        this.loadFacilities();
        this.loadAFTopics();
        this.loadAFTopics();
        this.loadAFStatusHistory();
    }

    public boolean isLoadComments() {
        return loadComments;
    }

    public void loadComments() {
        this.loadComments = true;
    }

    public boolean isLoadBasicInformation() {
        return loadBasicInformation;
    }

    public void loadBasicInformation() {
        this.loadBasicInformation = true;
    }

    public boolean isLoadDocument() {
        return loadDocument;
    }

    public void loadDocument() {
        this.loadDocument = true;
    }

    public boolean isLoadAFTopics() {
        return loadAFTopics;
    }

    public void loadAFTopics() {
        this.loadAFTopics = true;
    }

    public boolean isLoadFacilities() {
        return loadFacilities;
    }

    public void loadFacilities() {
        this.loadFacilities = true;
    }

    public boolean isLoadAFStatusHistory() {
        return loadAFStatusHistory;
    }

    public void loadAFStatusHistory() {
        this.loadAFStatusHistory = true;
    }

    public AFFacilityLoadOptionDTO getAfFacilityLoadOptionDTO() {
        return afFacilityLoadOptionDTO;
    }

    public void setAfFacilityLoadOptionDTO(AFFacilityLoadOptionDTO afFacilityLoadOptionDTO) {
        this.afFacilityLoadOptionDTO = afFacilityLoadOptionDTO;
    }

    public AFBasicInformationLoadOptionDTO getAfBasicInformationLoadOptionDTO() {
        return afBasicInformationLoadOptionDTO;
    }

    public void setAfBasicInformationLoadOptionDTO(AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO) {
        this.afBasicInformationLoadOptionDTO = afBasicInformationLoadOptionDTO;
    }
}
