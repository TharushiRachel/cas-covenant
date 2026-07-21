package com.itechro.cas.model.domain.customer;

import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER_RATINGS")
public class CustomerRatings extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_RATINGS")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_RATINGS", sequenceName = "SEQ_T_CUSTOMER_RATINGS", allocationSize = 1)
    @Column(name = "CUSTOMER_RATINGS_ID")
    private Integer customerRatingsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @Column(name = "ROA_PERCENTAGE_EXISTING")
    private Double existingFacilitiesROA;

    @Column(name = "ROA_PERCENTAGE_PROPOSED")
    private Double proposedFacilitiesROA;

    @Column(name = "RISK_GRADING")
    private String riskGrading;

    @Column(name = "RISK_SCORE")
    private Double riskScore;

    @Column(name = "RAM")
    private Double ramScore;



    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Double getExistingFacilitiesROA() {
        return existingFacilitiesROA;
    }

    public void setExistingFacilitiesROA(Double existingFacilitiesROA) {
        this.existingFacilitiesROA = existingFacilitiesROA;
    }

    public Double getProposedFacilitiesROA() {

        return proposedFacilitiesROA;
    }

    public void setProposedFacilitiesROA(Double proposedFacilitiesROA) {
        this.proposedFacilitiesROA = proposedFacilitiesROA;
    }

    public String getRiskGrading() {

        return riskGrading;
    }

    public void setRiskGrading(String riskGrading) {
        this.riskGrading = riskGrading;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public Double getRamScore() {
        return ramScore;
    }

    public void setRamScore(Double ramScore) {
        this.ramScore = ramScore;
    }

    public Integer getCustomerRatingsID() {
        return customerRatingsID;
    }

    public void setCustomerRatingsID(Integer customerRatingsID) {
        this.customerRatingsID = customerRatingsID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
    }
}
