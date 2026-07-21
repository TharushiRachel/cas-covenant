package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_FACILITY_RENTAL_INFO")
public class FacilityRentalInformation extends UserTrackableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_RENTAL_INFO")
    @SequenceGenerator(name = "SEQ_T_FACILITY_RENTAL_INFO", sequenceName = "SEQ_T_FACILITY_RENTAL_INFO", allocationSize = 1)
    @Column(name = "FACILITY_RENTAL_INFO_ID")
    private Integer facilityRentalInformationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "NO_OF_RENTALS")
    private Integer noOfRentals;

    @Column(name = "RENTAL_AMOUNT")
    private Double rentalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "DISPLAY_ORDER")
    private int displayOrder;

    public Integer getFacilityRentalInformationID() {
        return facilityRentalInformationID;
    }

    public void setFacilityRentalInformationID(Integer facilityRentalInformationID) {
        this.facilityRentalInformationID = facilityRentalInformationID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
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
}
