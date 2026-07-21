package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FacilityRepaymentContentDTO extends BaseContentDTO {

    @SerializedName("FACILITY REPAYMENT ID")
    private Integer facilityRepaymentID;

    @SerializedName("FACILITY ID")
    private Integer facilityID;

    @SerializedName("FACILITY REF CODE")
    private String facilityRefCode;

    @SerializedName("REPAYMENT TYPE")
    private String repaymentType;

    @SerializedName("LOAN TERM")
    private Integer loanTerm;

    @SerializedName("PAYMENT DETAIL")
    private String paymentDetail;

    @SerializedName("DOWN PAYMENT")
    private String downPayment;

    @SerializedName("REPAYMENT COMMENT")
    private String repaymentComment;

    @SerializedName("PAYMENT FREQUENCY")
    private String paymentFrequency;

    public Integer getFacilityRepaymentID() {
        return facilityRepaymentID;
    }

    public void setFacilityRepaymentID(Integer facilityRepaymentID) {
        this.facilityRepaymentID = facilityRepaymentID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilityRefCode() {
        return facilityRefCode;
    }

    public void setFacilityRefCode(String facilityRefCode) {
        this.facilityRefCode = facilityRefCode;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getRepaymentComment() {
        return repaymentComment;
    }

    public void setRepaymentComment(String repaymentComment) {
        this.repaymentComment = repaymentComment;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
