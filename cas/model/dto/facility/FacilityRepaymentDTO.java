package com.itechro.cas.model.dto.facility;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityRepayment;

import java.io.Serializable;

public class FacilityRepaymentDTO implements Serializable {

    private Integer facilityRepaymentID;

    private Integer facilityID;

    private Integer facilityPaperID;

    private DomainConstants.RepaymentType repaymentType;

    private DomainConstants.PaymentFrequency paymentFrequency;

    private Integer loanTerm;

    private String paymentDetail;

    private String downPayment;

    private String repaymentComment;

    public FacilityRepaymentDTO(){}

    public FacilityRepaymentDTO(FacilityRepayment facilityRepayment){
        this.facilityRepaymentID = facilityRepayment.getFacilityRepaymentID();
        this.facilityID = facilityRepayment.getFacility().getFacilityID();
        this.repaymentType = facilityRepayment.getRepaymentType();
        this.loanTerm = facilityRepayment.getLoanTerm();
        this.paymentDetail = facilityRepayment.getPaymentDetail();
        this.downPayment = facilityRepayment.getDownPayment();
        this.repaymentComment = facilityRepayment.getRepaymentComment();
        this.paymentFrequency = facilityRepayment.getPaymentFrequency();
    }

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

    public DomainConstants.RepaymentType getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(DomainConstants.RepaymentType repaymentType) {
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

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public DomainConstants.PaymentFrequency getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(DomainConstants.PaymentFrequency paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
