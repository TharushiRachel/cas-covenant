package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityRentalInformation;

import java.io.Serializable;

public class FacilityRentalInformationDTO implements Serializable {

    private Integer facilityRentalInformationID;

    private Integer facilityID;

    private Integer noOfRentals;

    private Double rentalAmount;

    private AppsConstants.Status status;

    private int displayOrder;

    public FacilityRentalInformationDTO() {

    }

    public FacilityRentalInformationDTO(FacilityRentalInformation facilityRentalInformation) {
        this.facilityRentalInformationID = facilityRentalInformation.getFacilityRentalInformationID();
        if (facilityRentalInformation.getFacility() != null) {
            this.facilityID = facilityRentalInformation.getFacility().getFacilityID();
        }
        this.noOfRentals = facilityRentalInformation.getNoOfRentals();
        this.rentalAmount = facilityRentalInformation.getRentalAmount();
        this.status = facilityRentalInformation.getStatus();
        this.displayOrder = facilityRentalInformation.getDisplayOrder();
    }

    public FacilityRentalInformationDTO(FacilityRentalInformation facilityRentalInformation, Integer facilityID) {
        this.facilityID = facilityID;
        this.facilityRentalInformationID = facilityRentalInformation.getFacilityRentalInformationID();
        this.noOfRentals = facilityRentalInformation.getNoOfRentals();
        this.rentalAmount = facilityRentalInformation.getRentalAmount();
        this.status = facilityRentalInformation.getStatus();
        this.displayOrder = facilityRentalInformation.getDisplayOrder();
    }

    public Integer getFacilityRentalInformationID() {
        return facilityRentalInformationID;
    }

    public void setFacilityRentalInformationID(Integer facilityRentalInformationID) {
        this.facilityRentalInformationID = facilityRentalInformationID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getNoOfRentals() {
        return noOfRentals;
    }

    public void setNoOfRentals(Integer noOfRentals) {
        this.noOfRentals = noOfRentals;
    }

    public Double getRentalAmount() {
        return rentalAmount;
    }

    public void setRentalAmount(Double rentalAmount) {
        this.rentalAmount = rentalAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "FacilityRentalInformationDTO{" +
                "facilityRentalInformationID=" + facilityRentalInformationID +
                ", facilityID=" + facilityID +
                ", noOfRentals=" + noOfRentals +
                ", rentalAmount=" + rentalAmount +
                ", status=" + status +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
