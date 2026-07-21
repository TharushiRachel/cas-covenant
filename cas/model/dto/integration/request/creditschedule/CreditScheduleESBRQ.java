package com.itechro.cas.model.dto.integration.request.creditschedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.integration.response.creditschedule.RentalDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditScheduleESBRQ {
    @JsonProperty("reqID")
    private String reqID;

    @JsonProperty("casReference")
    private String casReference;

    @JsonProperty("leaseCapital")
    private String leaseCapital;

    @JsonProperty("rentalAmt")
    private String rentalAmt;

    @JsonProperty("reducingRate")
    private String reducingRate;

    @JsonProperty("noOfTerms")
    private String noOfTerms;

    @JsonProperty("noOfUpfronts")
    private String noOfUpfronts;

    @JsonProperty("vatExempted")
    private String vatExempted;

    @JsonProperty("effectiveRate")
    private String effectiveRate;

    @JsonProperty("supplierName")
    private String supplierName;

    @JsonProperty("REL")
    private List<RentalDTO> REL;

    @JsonProperty("reqID")
    public String getReqID() {
        return reqID;
    }

    @JsonProperty("reqID")
    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    @JsonProperty("casReference")
    public String getCasReference() {
        return casReference;
    }

    @JsonProperty("casReference")
    public void setCasReference(String casReference) {
        this.casReference = casReference;
    }

    @JsonProperty("leaseCapital")
    public String getLeaseCapital() {
        return leaseCapital;
    }

    @JsonProperty("leaseCapital")
    public void setLeaseCapital(String leaseCapital) {
        this.leaseCapital = leaseCapital;
    }

    @JsonProperty("rentalAmt")
    public String getRentalAmt() {
        return rentalAmt;
    }

    @JsonProperty("rentalAmt")
    public void setRentalAmt(String rentalAmt) {
        this.rentalAmt = rentalAmt;
    }

    @JsonProperty("reducingRate")
    public String getReducingRate() {
        return reducingRate;
    }

    @JsonProperty("reducingRate")
    public void setReducingRate(String reducingRate) {
        this.reducingRate = reducingRate;
    }

    @JsonProperty("noOfTerms")
    public String getNoOfTerms() {
        return noOfTerms;
    }

    @JsonProperty("noOfTerms")
    public void setNoOfTerms(String noOfTerms) {
        this.noOfTerms = noOfTerms;
    }

    @JsonProperty("noOfUpfronts")
    public String getNoOfUpfronts() {
        return noOfUpfronts;
    }

    @JsonProperty("noOfUpfronts")
    public void setNoOfUpfronts(String noOfUpfronts) {
        this.noOfUpfronts = noOfUpfronts;
    }

    @JsonProperty("vatExempted")
    public String getVatExempted() {
        return vatExempted;
    }

    @JsonProperty("vatExempted")
    public void setVatExempted(String vatExempted) {
        this.vatExempted = vatExempted;
    }

    @JsonProperty("effectiveRate")
    public String getEffectiveRate() {
        return effectiveRate;
    }

    @JsonProperty("effectiveRate")
    public void setEffectiveRate(String effectiveRate) {
        this.effectiveRate = effectiveRate;
    }

    @JsonProperty("supplierName")
    public String getSupplierName() {
        return supplierName;
    }

    @JsonProperty("supplierName")
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @JsonProperty("REL")
    public List<RentalDTO> getREL() {
        return REL;
    }

    @JsonProperty("REL")
    public void setREL(List<RentalDTO> REL) {
        this.REL = REL;
    }

    @Override
    public String toString() {
        return "CreditScheduleESBRQ{" +
                "reqID='" + reqID + '\'' +
                ", casReference='" + casReference + '\'' +
                ", leaseCapital='" + leaseCapital + '\'' +
                ", rentalAmt='" + rentalAmt + '\'' +
                ", reducingRate='" + reducingRate + '\'' +
                ", noOfTerms='" + noOfTerms + '\'' +
                ", noOfUpfronts='" + noOfUpfronts + '\'' +
                ", vatExempted='" + vatExempted + '\'' +
                ", effectiveRate='" + effectiveRate + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", REL=" + REL +
                '}';
    }
}
